package pokemonGo;
/**
 * Created by youssefhammoud on 12/1/16.
 */
import pokemonGo.Node;
import pokemonGo.ReducedMatrix;

import java.io.PrintWriter;
import java.util.*;


public class BnB {

    private static class LbComparator implements Comparator<Node>{
        @Override
        public int compare(Node x, Node y){

            if (x.getPath().getLocations().size() < y.getPath().getLocations().size()) {
                return 1;
            } else if (x.getPath().getLocations().size() > y.getPath().getLocations().size()) {
                return -1;
            } else {
                if (x.getLowerBound() < y.getLowerBound()) {
                    return -1;
                } else if (x.getLowerBound() > y.getLowerBound()){
                    return 1;
                } else {
                    return (int) x.getPath().getTotalDistance() - (int) y.getPath().getTotalDistance();
                }
            }
//            if (x.getLowerBound() < y.getLowerBound()) {
//                return -1;
//            } else if (x.getLowerBound() > y.getLowerBound()){
//                return 1;
//            } else {
//                return (int) x.getPath().getTotalDistance() - (int) y.getPath().getTotalDistance();
//            }
        }
    }
    private LbComparator comparator = new LbComparator();
    private double[][] adj;
    private Map<Integer, Location> locations;
    private int n; // number of vertices
    double initial;
    private Tour bestTour;
    private Random randy;
    public PrintWriter output;
    String outputFile;
    long cutoff_time;



    public BnB(ArrayList<Location> adj, HashMap locations, long cutOff, int seed, String path, String city) throws Exception{
        // Need a seed to shuffle
        randy = new Random(seed);
        initial = new Tour(adj, randy).getTotalDistance();
        n = adj.size();
        this.adj = new double[n][n];
        for (int i = 0; i < adj.size(); i++) {
            for (int j = 0; j< adj.size(); j++) {
                if (i != j) {
                    double dist = adj.get(i).distanceTo(adj.get(j));
                    this.adj[i][j] = dist;
                } else {
                    this.adj[i][j] = Double.MAX_VALUE;
                }
            }
        }
        this.locations = new HashMap<Integer, Location>();
        this.locations.putAll(locations);
        this.cutoff_time = (long) (cutOff * Math.pow(10, 9));
        outputFile = path + city + "_BnB_" + cutOff + ".trace";
        this.output = new PrintWriter(outputFile, "UTF-8");
    }


    public Tour findOptimal(){

        PriorityQueue<Node> queue = new PriorityQueue<Node>(comparator);
        double bestTourCost = Double.MAX_VALUE / 100;
        System.out.println(bestTourCost);
        ReducedMatrix initialMatrix = new ReducedMatrix(adj);
        initialMatrix.rowReduction();
        initialMatrix.columnReduction();
        Node dumParent = new Node(-1);
        Node start = new Node(dumParent, locations.get(1), new Tour(new ArrayList<>()), initialMatrix);
        start.appendPath();
        queue.add(start);
        int size = 0;
        Node current = new Node(-1);
        Node best = new Node(-1);
        while(!queue.isEmpty()) {
            current = queue.poll();
            double parentLB = current.computeLowerBound();
            if (current.getLowerBound() < bestTourCost) {
                current.setLevel(current.getParent().getLevel() + 1);
                System.out.println("ID: " + current.getLocation().getId() + " Level: " + current.getLevel() + " LB: " + current.getLowerBound());
                size = queue.size();
                if (current.getLevel() == n - 1) {
                    current.appendPath(start.getLocation());
                    double currentPath = current.computePath();
                    if (parentLB < bestTourCost) {
                        bestTourCost = parentLB;
                        best = current;
                    }
                } else {
                    for (int i = 2; i <= n; i++) {
                        if (!current.getPath().containsLocation(locations.get(i))) {
                            Node temp = new Node(current, locations.get(i), current.getPath(), current.getRm());
                            temp.appendPath();
                            temp.computeLowerBound();
                            if (temp.getLowerBound() < bestTourCost) {
                                queue.add(temp);
                            }
                        }
                    }
                }
            }
        }

//        System.out.println("Queue size: " + size);
        System.out.println(best.getPath().toString());
        System.out.println(bestTourCost);
        return best.getPath();
    }
}
