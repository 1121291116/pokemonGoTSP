import java.util.*;

/**
 * Created by chenzhijian on 11/19/16.
 */
public class ILS {
    private Tour bestTour;
    Map<Integer, int[]> swapMap;
    ArrayList<Integer> swapIds;
    double[][] distanceMatrix;
    int numIteration;
    int totalIter;
    Random randy;




    public ILS(Tour t) {
        bestTour = t;
        constructSwapMap(t);
        numIteration = 5000000;
        totalIter = 0;
        randy = new Random();
    }

    public void run() {
        iterativeHillClimbing(bestTour);
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
//        tour = hillClimbing(tour);
//        this.currentCost = tourLength(tour);
        int peak = 0;
        while (totalIter < numIteration) {
            totalIter++;
            if (totalIter % 10000 == 1) {
                System.out.println("Repeating" + totalIter);
            }
            tour = hillClimbing(tour);
            peak ++;
            System.out.println("peak: " + peak);
            System.out.println("peak height: " + tour.getTotalDistance());
            if (tour.getTotalDistance() < bestTour.getTotalDistance()) {
                bestTour = tour;
            }
            tour = doubleBridgeMove(tour);
        }

        System.out.println("final");
        System.out.println(bestTour.getTotalDistance());
        System.out.println("swap:" + swapIds.size());
        tour.printTour();
        return tour;
    }


    private Tour hillClimbing(Tour tour) {
        //evaluate current tour cost
        //generate new tours based on current
        //compare
        Tour localBest = tour;
        int iter = totalIter;
        HashSet<Integer> swapped = new HashSet<>();

        while (iter < numIteration) {

            int swap = randy.nextInt(swapMap.size());
            if (!swapped.contains((swap))) {
                swapped.add(swap);
                Tour newTour = twoOpt(tour, swap);
                iter++;//Made one swap
                if (newTour.getTotalDistance() < tour.getTotalDistance()) {
                    //If find a better one, start over from current solution

                    tour = newTour;
                    localBest = newTour;
                    swapped = new HashSet<>();
//                    System.out.println("iter: " + (iter - 1) + " " + newCost);
                }
            }


            if (swapped.size() == swapMap.size()) {
                break;
            }
        }
        System.out.println("**************");
        System.out.println(iter - totalIter);
        totalIter = iter;
        return localBest;

    }

    private Tour twoOpt(Tour candidate, int swapId) {
//        System.out.println("before 2 opt");
//        candidate.printTour();
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
//        System.out.println("after 2 opt");
//        newTour.printTour();
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
