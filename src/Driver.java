package pokemonGo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

public class Driver {
    public static void main(String[] args) throws Exception {
        String city_file = args[0];
        BufferedReader br = new BufferedReader(new FileReader(city_file));
        String line = null;
        HashMap m = new HashMap();

        ArrayList<Location> route = new ArrayList<Location>();
        int i = 0;
        System.out.println("-------------------------------------------");
        while (i <= 4) {
            System.out.println(br.readLine());
            i++;
        }
        System.out.println("-------------------------------------------");
        while ((line = br.readLine()) != null && !line.contains("EOF")) {
            String[] splitLine = line.split(" ");
            int id = Integer.parseInt(splitLine[0]);
            double longitude = Double.parseDouble(splitLine[1]);
            double lat = Double.parseDouble(splitLine[2]);
            Location l = new Location(id, longitude, lat);
            route.add(l);
            m.put(id, l);
        }
        
        /*
        Route r = new Route(route);

        SimulatedAnnealing sa = new SimulatedAnnealing(0.95, 1.0, 0.00001, 50);
        Route best_route = sa.findRoute(r);
        ArrayList<Location> best = best_route.getLocations();



        Set<Route> s = sa.getVisited();
        Route min = best_route;
        for (Route x : s) {
            if (x.getTotalDistance() < min.getTotalDistance()) {
                min = x;
            }
        }
 		
        System.out.println(min.getTotalDistance());
		*/
        
        
        //MST-Approximation
        MST mst = new MST(route);
        LinkedList<Edge> MST = mst.buildMST();      
        MstApproximation mstApp = new MstApproximation(route.size(), MST);
        LinkedList<Integer> TSP = mstApp.findTSP();
        
        //Nearest Neighbor
//        NearestNeighbor nearNb = new NearestNeighbor(m);
//        LinkedList<Integer> TSP = nearNb.findTSP();
        
        /*
         * Calculate total distance
         */
        int previous = 1;
        double total = 0;
        for (int s : TSP){
        	Location temp = (Location) m.get(s);
        	total = total + temp.distanceTo((Location) m.get(previous));
        	previous = s;
        }

        for(int s : TSP){
        	System.out.print(s + ", ");
        }
        System.out.println(total);
        
    }
}
