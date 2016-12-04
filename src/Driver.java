/**
 * Created by chenzhijian on 11/19/16.
 */
import pokemonGo.*;
import java.io.BufferedReader;
import java.io.FileReader;
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
            Tour r = new Tour(route, ils.getRandy());
            r.printTour();
            ils.run(r);
        } else if (alg.equals("LS2")) {
            SimulatedAnnealing sa = new SimulatedAnnealing(city, 1 - Math.pow(10, -6), 1.0, 0.00001, seed, cutoff);
            Tour r = new Tour(route, sa.getRandy());
            r.printTour();
            Tour best_route = sa.findtour(r);
            sa.output.close();
            System.out.println("Global minimum: " + best_route.getTotalStringDistance());
        } else {
            System.out.println("INVALID INPUT");
        }




    }
}