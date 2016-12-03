import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Set;


public class Driver {

    public static void main(String[] args) throws Exception {
        String city_file = args[0];
        BufferedReader br = new BufferedReader(new FileReader(city_file));
        String line = null;

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
        }


        Tour r = new Tour(route, true);
        SimulatedAnnealing sa = new SimulatedAnnealing(1 - Math.pow(10, -6), 1.0, 0.00001, 50, (long)6e+11);
        Tour best_route = sa.findtour(r);
        System.out.println("Global minimum: " + best_route.getTotalStringDistance());



//
//        BnB bnb = new BnB(route);
//        double[][] e = {{1000,10,8,9,7},{10,1000,10,5,6},{8,10,1000,8,9},{9,5,8,1000,6},{7,6,9,6,1000}};
//        double[][] rowReduced = bnb.rowReduction(e);
//
//        System.out.println();
//        for (i = 0; i < 5; i++) {
//            for (int j = 0; j< 6; j++) {
//                System.out.print(rowReduced[i][j] + "\t");
//            }
//            System.out.println();
//
//        }
//        System.out.println();
//
//        System.out.println();
//
//        double[][] columnReduced = bnb.columnReduction(rowReduced);
//        int n = columnReduced.length;
//
//        for (i = 0; i < n; i++) {
//            for (int j = 0; j< n; j++) {
//                System.out.print(columnReduced[i][j] + "\t");
//            }
//            System.out.println();
//
//        }

    }
}