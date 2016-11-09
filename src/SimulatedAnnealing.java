import java.util.HashSet;
import java.util.Set;

public class SimulatedAnnealing {

    double init_temperature;
    double alpha;
    double min_temperature;
    Set<Location> swapped = new HashSet<Location>();
    Set<Route> visited = new HashSet<Route>();

    public SimulatedAnnealing(double alpha, double init_temperature, double min_temperature) {
        this.init_temperature = init_temperature;
        this.min_temperature = min_temperature;
        this.alpha = alpha;
    }

    public boolean acceptance_probability(double old_cost, double new_cost, double temp) {
        double ap = Math.exp((old_cost-new_cost)/temp);
        double r = Math.random();

        if (ap > r) {
            return true;
        }
        return false;
    }

    public Route findRoute(Route route, double temperature) {
        double dist_old = route.getTotalDistance();
        while (temperature > min_temperature) {
            int i = 1;
            while (i <= 10000) {
                Route new_route = adjacentRoute(route);
                if (visited.add(new_route)) {
                    double dist_new = new_route.getTotalDistance();
                    if (dist_new < dist_old) {
                        route = new_route;
                        dist_old = dist_new;
                    } else {
                        boolean ap = acceptance_probability(dist_old, dist_new, temperature);
                        if (ap) {
                            route = new_route;
                            dist_old = dist_new;
                        }
                    }
                }
                i++;
            }
            temperature = temperature * alpha;

            System.out.println("Temperature:  " + temperature + " | Distance: " + route.getTotalStringDistance());
        }
        return route;
    }

    public Route adjacentRoute(Route route) {
        int index1 = 0;
        int index2 = 0;

        while (index1 == index2) {
            index1 = (int) (route.getLocations().size() * Math.random());
            index2 = (int) (route.getLocations().size() * Math.random());
        }

        Location l1 = route.getLocations().get(index1);
        Location l2 = route.getLocations().get(index2);

        route.getLocations().set(index1, l2);
        route.getLocations().set(index2, l1);

        return route;

    }


}