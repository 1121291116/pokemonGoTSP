//import java.util.*;
//
///**
// * Created by chenzhijian on 11/19/16.
// */
//public class ILS {
//    private Tour bestTour;
//    Map<Integer, int[]> swapMap;
//    ArrayList<Integer> swapIds;
//    double[][] distanceMatrix;
//    int numIteration;
//    int totalIter;
//    Random randy;
//
//
//
//
//    public ILS(Tour t) {
//        bestTour = t;
//        constructSwapMap(t);
//
//    }
//
//    private void constructSwapMap(Tour t) {
//        swapMap = new HashMap<>();
//        swapIds = new ArrayList<>();
//        distanceMatrix = new double[t.getSize()][t.getSize()];
//        int swapId = 0;
//        for (int i = 0; i < t.getSize(); i++) {
//            for (int j = 0; j < t.getSize(); j++) {
//                distanceMatrix[i][j] = t.getLocations().get(i).distanceTo(t.getLocations().get(j));
//                if (i < j) {
//                    this.swapMap.put(swapId, new int[] {i, j});
//                    swapIds.add(swapId);
//                    swapId++;
//                }
//            }
//        }
//    }
//
//    private int[] iterativeHillClimbing(Tour tour) {
//        tour = hillClimbing(tour);
//        this.currentCost = tourLength(tour);
//        int peak = 0;
//        while (totalIter < numIteration) {
//            totalIter++;
//            if (totalIter % 10000 == 1) {
//                System.out.println("Repeating" + totalIter);
//            }
//            int[] newTour = doubleBridgeMove(tour);
//            newTour = hillClimbing(newTour);
//            System.out.println("peak: " + peak);
//            System.out.println("peak height: " + tourLength(newTour));
//            if (tourLength(newTour) < tourLength(tour)) {
//                tour = newTour;
//                this.currentCost = tourLength(tour);
//            }
//        }
//
//        System.out.println("final");
//        System.out.println(currentCost);
//        printTour(tour);
//        return tour;
//    }
//
//
//    private Tour hillClimbing(Tour tour) {
//        //evaluate current tour cost
//        //generate new tours based on current
//        //compare
//        double localMin = tour.getTotalDistance();
//        double newCost;
//        int iter = totalIter;
//        HashSet<Integer> swapped = new HashSet<>();
//
//        while (iter < numIteration) {
//
//            int swap = randy.nextInt(swapIds.size());
//            if (!swapped.contains((swap))) {
//                swapped.add(swap);
//                int[] newTour = twoOpt(tour, swap);
//                newCost = tourLength(newTour);
//                iter++;//Made one swap
//                if (newCost < localMin) {
//                    //If find a better one, start over from current solution
//
//                    tour = newTour;
//                    localMin = newCost;
//                    swapped = new HashSet<>();
//                    System.out.println("iter: " + (iter - 1) + " " + newCost);
//                }
//            }
//
//
//            if (swapped.size() == swaps.size()) {
//                break;
//            }
//        }
//        totalIter = iter;
//        return tour;
//
//    }
//
//    private Tour twoOpt(Tour candidate, int swapId) {
//        int length = candidate.getSize();
//        int[] output = new int[length];
//        int cut1 = swapMap.get(swapId)[0];
//        int cut2 = swapMap.get(swapId)[1];
//        List<Location> p1 = candidate.getLocations().subList(0, cut1);
//        List<Location> p2 = candidate.getLocations().subList(cut1, cut2);
//        Collections.reverse(p2);
//        List<Location> p3 = candidate.getLocations().subList(cut2, length);
//        p1.addAll(p2);
//        p1.addAll(p3);
//        Tour newTour = new Tour(new ArrayList<>(p1));
//        return newTour;
//
////        System.arraycopy(candidate, 0, output, 0, cut1);
////        int[] middle = new int[cut2 - cut1];
////        System.arraycopy(candidate, cut1, middle, 0, cut2 - cut1);
////        for (int i = 0; i < cut2 - cut1; i ++) {
////            middle[i] = candidate[cut2 - i - 1];
////        }
////        System.arraycopy(middle, 0, output, cut1, cut2 - cut1);
////        System.arraycopy(candidate, cut2, output, cut2, candidate.length - cut2);
////        return output;
//    }
//
//
//
//
//}
