import java.util.HashSet;
import java.util.Set;

public class SimulatedAnnealing {

    private double init_temperature;
    private double alpha;
    private double min_temperature;
    private int seed;
    private Set<Route> visited = new HashSet<Route>();

    public SimulatedAnnealing(double alpha, double init_temperature, double min_temperature, int seed) {
        this.init_temperature = init_temperature;
        this.min_temperature = min_temperature;
        this.alpha = alpha;
        this.seed = seed;
    }

    public double getInit_temperature() {
        return init_temperature;
    }

    public double getAlpha() {
        return alpha;
    }

    public double getMin_temperature() {
        return min_temperature;
    }

    public int getSeed() {
        return seed;
    }

    public Set<Route> getVisited() {
        return visited;
    }

    public boolean acceptance_probability(double dist_old, double dist_new, double temp) {
        double ap = Math.exp((dist_old-dist_new)/temp);
        double r = Math.random();

        if (ap > r) {
            return true;
        }
        return false;
    }

    public Route findRoute(Route route) {
        double temperature = getInit_temperature();

        Route best_route = route;
        double dist_old = best_route.getTotalDistance();

        while (temperature > min_temperature) {

            int i = 1;

            while (i <= seed) {
                Route new_route = adjacentRoute(best_route);

                double dist_new = new_route.getTotalDistance();
                if (dist_new < dist_old) {
                    best_route = new_route;
                    dist_old = dist_new;
                } else {
                    boolean ap = acceptance_probability(dist_old, dist_new, temperature);
                    if (ap) {
                        best_route = new_route;
                        dist_old = dist_new;
                    }
                }
                visited.add(new Route(best_route));
                i++;
            }
            temperature = temperature * alpha;
            System.out.println("Temperature:  " + temperature + " | Distance: " + best_route.getTotalStringDistance());
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