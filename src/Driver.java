import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

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

        Route r = new Route(route);

        SimulatedAnnealing sa = new SimulatedAnnealing(0.85, 1.0, 0.00001);
        Route best_route = sa.findRoute(r, sa.init_temperature);
        ArrayList<Location> best = best_route.getLocations();

        for (Location l : best) {
            System.out.print(l.getId() + " --> ");
        }
        System.out.println(best.get(0).getId());
        System.out.println(best_route.getTotalStringDistance());
    }
}