import java.lang.reflect.Array;
import java.util.*;

public class SimulatedAnnealing {

    private double init_temperature;
    private double alpha;
    private double min_temperature;
    private int seed;
    Random rand;
    long cutoff_time;

    private Set<Tour> visited = new HashSet<Tour>();

    public SimulatedAnnealing(double alpha, double init_temperature, double min_temperature, int seed, long cutOff) {
        this.init_temperature = init_temperature;
        this.min_temperature = min_temperature;
        this.alpha = alpha;
        this.rand = new Random(seed);
        this.seed = seed;
        this.cutoff_time = cutOff;
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
        double ap = Math.exp(((dist_old-dist_new)/Math.pow(10,4))/temp);
        double r = this.rand.nextDouble();

        if (ap > r) {
            return true;
        }
        return false;
    }

    public Tour findtour(Tour tour) {
        long start_time = System.nanoTime();
        double temperature = getInit_temperature();
        int jumps = 0;
        Tour best_tour = tour;
        double best_dist = best_tour.getTotalDistance();
        Tour globalBest = tour;
        while (temperature > min_temperature)  {
            if (System.nanoTime() - start_time >= cutoff_time) {
                return globalBest;
            }
            Tour new_tour = twoOpt(best_tour);
            double dist_new = new_tour.getTotalDistance();
            boolean ap = acceptance_probability(best_dist, dist_new, temperature);
            if (dist_new < best_dist || ap) {
                best_tour = new_tour;
                best_dist = dist_new;
                if (dist_new < globalBest.getTotalDistance()) {
                    globalBest = new_tour;
                    long timestamp = System.nanoTime() - start_time;
                    System.out.println(timestamp + "\t" + globalBest);

                    //System.out.println((System.nanoTime() - start_time)/(1e-6) + " | " + globalBest.getTotalDistance());
                }
            }
            temperature = temperature * alpha;
        }
        return globalBest;
    }

//    public Tour adjacenttour(Tour tour) {
//        int index1 = 0;
//        int index2 = 0;
//        Tour new_tour = new Tour(tour);
//        while (index1 == index2) {
//            index1 = (int) (tour.getLocations().size() * rand.nextDouble());
//            index2 = (int) (tour.getLocations().size() * rand.nextDouble());
//        }
//
//        Location l1 = new_tour.getLocations().get(index1);
//        Location l2 = new_tour.getLocations().get(index2);
//
//        new_tour.getLocations().set(index1, l2);
//        new_tour.getLocations().set(index2, l1);
//
//        return new_tour;
//
//    }

    private Tour twoOpt(Tour candidate) {

        int length = candidate.getLocations().size();
        Location[] output = new Location[length];
        int cut1 = 0;
        int cut2 = 0;
        while (cut1 >= cut2) {
            cut1 = (int) (candidate.getLocations().size() * rand.nextDouble());
            cut2 = (int) (candidate.getLocations().size() * rand.nextDouble());
        }
        Location[] array = candidate.getLocations().toArray(new Location[length]);

        System.arraycopy(array, 0, output, 0, cut1);
        Location[] middle = new Location[cut2 - cut1];
        System.arraycopy(array, cut1, middle, 0, cut2 - cut1);
        for (int i = 0; i < cut2 - cut1; i ++) {
            middle[i] = array[cut2 - i - 1];
        }
        System.arraycopy(middle, 0, output, cut1, cut2 - cut1);
        System.arraycopy(array, cut2, output, cut2, array.length - cut2);
        ArrayList<Location> newList = new ArrayList<>(
                Arrays.asList(output)
        );

        Tour newTour = new Tour(newList, false);

        return newTour;
    }







}