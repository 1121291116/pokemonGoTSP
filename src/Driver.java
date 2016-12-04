/**
 * Created by chenzhijian on 11/19/16.
 */
import pokemonGo.*;

import java.io.*;
import java.util.ArrayList;

public class Driver {

    public static void main(String[] args) throws Exception {
        String city_file = args[0];
        String alg = args[1];
        int cutoff = Integer.parseInt(args[2]);
        int seed = Integer.parseInt(args[3]);
        String path = args[4];
        //Check validity

        //Read corresponding input file
        BufferedReader br = new BufferedReader(new FileReader(city_file));
        String line = null;

        ArrayList<Location> route = new ArrayList<>();
        int i = 0;
        String city = br.readLine().split(" ")[1];

        while(i <= 3) {
            line = br.readLine();
            i++;
            System.out.println(line);
        }

        while ((line = br.readLine()) != null && !line.contains("EOF")) {
            String[] splitLine = line.split(" ");
            int id = Integer.parseInt(splitLine[0]);
            double longitude = Double.parseDouble(splitLine[1]);
            double lat = Double.parseDouble(splitLine[2]);
            Location l = new Location(id, longitude, lat);
            route.add(l);
        }



        if (alg.equals("LS1")) {
            IterativeLocalSearch ils = new IterativeLocalSearch(city, cutoff, seed, path);
//            Tour r = new Tour(route, ils.getRandy());
            Tour r = new Tour(route);
            r.printTour();
            Tour bestTour = ils.run(r);
            printSolution(bestTour, path, city, alg, cutoff, seed);
        } else if (alg.equals("LS2")) {
            SimulatedAnnealing sa = new SimulatedAnnealing(city, 1 - Math.pow(10, -6), 1.0, 0.00001, seed, cutoff, path);
            Tour r = new Tour(route, sa.getRandy());
            r.printTour();
            Tour bestTour = sa.findtour(r);
            sa.output.close();
            System.out.println("Global minimum: " + bestTour.getTotalStringDistance());
            printSolution(bestTour, path, city, alg, cutoff, seed);
        } else {
            System.out.println("INVALID INPUT");
        }
    }

    public static void printSolution(Tour bestTour, String path, String city, String alg, int cutoff, int seed) throws FileNotFoundException, UnsupportedEncodingException {
        String solutionFile = path + city + "_" + alg + "_" + cutoff + "_" + seed + ".sol";
        PrintWriter solution = new PrintWriter(solutionFile, "UTF-8");

        ArrayList<Location> locations = bestTour.getLocations();
        int cost;
        int tourSize = bestTour.getSize();
        solution.format("%d%n", (int)bestTour.getTotalDistance());

        for (int i = 0; i < tourSize - 1; i++) {
            cost = (int)locations.get(i).distanceTo(locations.get(i + 1));
            solution.format("%d %d %d%n", locations.get(i).getId(), locations.get(i+1).getId(), cost);
        }

        cost = (int)locations.get(tourSize-1).distanceTo(locations.get(0));
        solution.format("%d %d %d%n", locations.get(tourSize-1).getId(), locations.get(0).getId(), cost);
        solution.close();
    }
}