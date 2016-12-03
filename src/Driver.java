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
        String alg = args[1];
        int cutoff = Integer.parseInt(args[2]);
        int seed = Integer.parseInt(args[3]);

        //Check validity


        // String inst = args[2];
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

        
        // System.out.println("-------------------------------------------");
        // while (i <= 4) {
        //     System.out.println(br.readLine());
        //     // br.readLine();
        //     i++;
        // }
        // System.out.println("-------------------------------------------");
        while ((line = br.readLine()) != null && !line.contains("EOF")) {
            String[] splitLine = line.split(" ");
            int id = Integer.parseInt(splitLine[0]);
            double longitude = Double.parseDouble(splitLine[1]);
            double lat = Double.parseDouble(splitLine[2]);
            Location l = new Location(id, longitude, lat);
            route.add(l);
        }





        if (alg.equals("LS1")) {
            System.out.println("LS1");
        }

        Tour r = new Tour(route);
        r.printTour();
        ILS sol = new ILS(r, city, cutoff, seed);
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