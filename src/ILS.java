import java.util.*;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * Created by chenzhijian on 11/19/16.
 */
public class ILS {
    private Tour bestTour;
    Map<Integer, int[]> swapMap;
    ArrayList<Integer> swapIds;
    double[][] distanceMatrix;
    Random randy;
    double currentCost;
    long timeElapsed;
    long duration;
    long startTime;

    PrintWriter output;
    String outputFile;



    public ILS(Tour t, String city, int cutoff, int seed, String path) throws IOException {
        bestTour = t;
        constructSwapMap(t);
        timeElapsed = 0;
        this.duration = cutoff * 1000;
        randy = new Random(seed);
        outputFile = path + city + "_LS1_" + cutoff + "_" + seed + ".trace";
        this.output = new PrintWriter(outputFile, "UTF-8");
        System.out.println(outputFile);
    }

    public void run() {
        iterativeHillClimbing(bestTour);
        output.close();
    }


    private void constructSwapMap(Tour t) {
        swapMap = new HashMap<>();
        swapIds = new ArrayList<>();
        distanceMatrix = new double[t.getSize()][t.getSize()];
        int swapId = 0;
        for (int i = 0; i < t.getSize(); i++) {
            for (int j = 0; j < t.getSize(); j++) {
                distanceMatrix[i][j] = t.getLocations().get(i).distanceTo(t.getLocations().get(j));
                if (i < j) {
                    this.swapMap.put(swapId, new int[] {i, j});
                    swapIds.add(swapId);
                    swapId++;
                }
            }
        }
    }

    private Tour iterativeHillClimbing(Tour tour) {
        this.currentCost = bestTour.getTotalDistance();
        this.startTime = System.currentTimeMillis();

        while (timeElapsed < duration) {
            timeElapsed = System.currentTimeMillis() - startTime;
            tour = hillClimbing(tour);
            double newTourLength = tour.getTotalDistance();

            if (newTourLength < currentCost) {
                bestTour = tour;
                this.currentCost = newTourLength;
            }
            tour = doubleBridgeMove(tour);
        }

        tour.printTour();
        return tour;
    }


    private Tour hillClimbing(Tour tour) {
        //evaluate current tour cost
        //generate new tours based on current
        //compare
        double localMin = tour.getTotalDistance();
        double newCost;
        HashSet<Integer> swapped = new HashSet<>();

        while (System.currentTimeMillis() - startTime < duration) {

            int swap = randy.nextInt(swapMap.size());
            if (!swapped.contains((swap))) {
                swapped.add(swap);
                Tour newTour = twoOpt(tour, swap);
                newCost = newTour.getTotalDistance();
                if (newCost < localMin) {
                    //If find a better one, start over from current solution
                    tour = newTour;
                    localMin = newCost;
                    swapped = new HashSet<>();

                    if (localMin < currentCost) {
                        currentCost = localMin;
                        double ts = ((double)(System.currentTimeMillis() - startTime)) / 1000;
                        System.out.println(ts + "\t" + currentCost);
                        output.format("%.3f\t%f%n", ts, currentCost);

                    }
                }
            }


            if (swapped.size() == swapMap.size()) {
                break;
            }
        }
        timeElapsed = System.currentTimeMillis() - startTime;
        // return localBest;
        return tour;
    }

    private Tour twoOpt(Tour candidate, int swapId) {
        int length = candidate.getSize();
        Location[] output = new Location[length];
        int cut1 = swapMap.get(swapId)[0];
        int cut2 = swapMap.get(swapId)[1];
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

        Tour newTour = new Tour(newList);
        return newTour;
    }

    // Implement double bridge move to create a perturbation
    private Tour doubleBridgeMove(Tour tour) {
        int length = tour.getLocations().size();
        Location[] perturbed = new Location[length];
        int cut1 = 1 + randy.nextInt(length / 4);
        int cut2 = 1 + cut1 + randy.nextInt(length / 4);
        int cut3 = 1 + cut2 + randy.nextInt(length/ 4);
        Location[] array = tour.getLocations().toArray(new Location[length]);
        System.arraycopy(array, 0, perturbed, 0, cut1);
        System.arraycopy(array, cut3, perturbed, cut1, length - cut3);
        System.arraycopy(array, cut2, perturbed, cut1 + length - cut3, cut3 - cut2);
        System.arraycopy(array, cut1, perturbed, cut1 + length - cut2, cut2 - cut1);
        ArrayList<Location> newList = new ArrayList<>(
                Arrays.asList(perturbed)
        );
        return new Tour(newList);
    }




}
