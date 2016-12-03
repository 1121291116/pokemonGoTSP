/**
 * Created by chenzhijian on 11/19/16.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Set;

public class Driver {

    public static void main(String[] args) throws Exception {
        String city_file = args[0];
        // int seed = Integer.parseInt(args[1]);
        // String inst = args[2];
        BufferedReader br = new BufferedReader(new FileReader(city_file));
        String line = null;

        ArrayList<Location> route = new ArrayList<>();
        int i = 0;
        // System.out.println("-------------------------------------------");
        while (i <= 4) {
            System.out.println(br.readLine());
            // br.readLine();
            i++;
        }
        // System.out.println("-------------------------------------------");
        while ((line = br.readLine()) != null && !line.contains("EOF")) {
            String[] splitLine = line.split(" ");
            int id = Integer.parseInt(splitLine[0]);
            double longitude = Double.parseDouble(splitLine[1]);
            double lat = Double.parseDouble(splitLine[2]);
            Location l = new Location(id, longitude, lat);
            route.add(l);
        }



        Tour r = new Tour(route);
        ILS sol = new ILS(r);
        sol.run();

//        SimulatedAnnealing sa = new SimulatedAnnealing(0.95, 1.0, 0.00001, 1000);
//        Tour best_route = sa.findtour(r);
//        ArrayList<Location> best = best_route.getLocations();



//        Set<Tour> s = sa.getVisited();
////        Tour min = best_route;
//        for (Tour x : s) {
//            if (x.getTotalDistance() < min.getTotalDistance()) {
//                min = x;
//            }
//        }
//
//        System.out.println(min.getTotalDistance());


    }
}