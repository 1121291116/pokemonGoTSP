import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SimulatedAnnealing {

    private double init_temperature;
    private double alpha;
    private double min_temperature;
    private int seed;
    Random rand;
    private Set<Tour> visited = new HashSet<Tour>();

    public SimulatedAnnealing(double alpha, double init_temperature, double min_temperature, int seed) {
        this.init_temperature = init_temperature;
        this.min_temperature = min_temperature;
        this.alpha = alpha;
        this.rand = new Random(seed);
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

    public Set<Tour> getVisited() {
        return visited;
    }

    public boolean acceptance_probability(double dist_old, double dist_new, double temp) {
        double ap = Math.exp(((dist_old-dist_new)/Math.pow(10, 8))/temp);
        double r = Math.random();


        if (ap > r) {
            return true;
        }
        return false;
    }

    public Tour findtour(Tour tour) {
        double temperature = getInit_temperature();

        Tour best_tour = tour;
        double dist_old = best_tour.getTotalDistance();

        while (temperature > min_temperature) {

                Tour new_tour = adjacenttour(best_tour);

                double dist_new = new_tour.getTotalDistance();
                if (dist_new < dist_old) {
                    best_tour = new_tour;
                    dist_old = dist_new;
                } else {
                    boolean ap = acceptance_probability(dist_old, dist_new, temperature);
                    if (ap) {
                        best_tour = new_tour;
                        dist_old = dist_new;
                    }
                }

                visited.add(new Tour(best_tour));

            temperature = temperature * alpha;
        }
        return tour;
    }

    public Tour adjacenttour(Tour tour) {
        int index1 = 0;
        int index2 = 0;

        while (index1 == index2) {
            index1 = (int) (tour.getLocations().size() * Math.random());
            index2 = (int) (tour.getLocations().size() * Math.random());
        }

        Location l1 = tour.getLocations().get(index1);
        Location l2 = tour.getLocations().get(index2);

        tour.getLocations().set(index1, l2);
        tour.getLocations().set(index2, l1);

        return tour;

    }


}