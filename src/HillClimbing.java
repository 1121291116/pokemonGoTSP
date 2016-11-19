import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;

public class HillClimbing {


	String inputFile;
	String city;
	//Dimension
	int dim;
	double[][] nodes;
	double[][] matrixD;
	Random randy;
	HashMap<Integer, int[]> swaps;
	ArrayList<Integer> swapIds;
	int[] tour;
	double currentCost;
	int numIteration;
	int totalIter;

	public HillClimbing(String inputFile) throws IOException {
		this.inputFile = inputFile;
		this.randy = new Random();
		this.numIteration = 500000;
		this.totalIter = 0;
		run();
	}


	private void run() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(inputFile));
		String line = br.readLine();
		String[] split = line.split(": ");
		this.city = split[1];

		br.readLine();

		split = br.readLine().split(": ");
		this.dim = Integer.parseInt(split[1]);

		this.nodes = new double[this.dim][2];
		this.tour = new int[this.dim];
		this.matrixD = new double[this.dim][this.dim];

		this.swaps = new HashMap<>();
		this.swapIds = new ArrayList<>();
		
		br.readLine();
		br.readLine();
		// System.out.println(dim);
		// 
		for (int i = 0; i < dim; i++) {
			split = br.readLine().split(" ");
			nodes[i][0] = Double.parseDouble(split[1]);
			nodes[i][1] = Double.parseDouble(split[2]);
			tour[i] = i;
		}
		br.close();

		//Fill in the distance matrixD
		int swapId = 0;
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				matrixD[i][j] = dist(i, j);
				if (i < j) {
					this.swaps.put(swapId, new int[] {i, j});
					swapIds.add(swapId);
					swapId++;
				}
			}
		}



		// for (int i : swaps.keySet()) {
		// 	System.out.println(i + " " + swaps.get(i)[0] + " " + swaps.get(i)[1]);
		// }

		// HashSet<Integer> myset = swaps.keySet();

		// Collections.shuffle(myset);
		// Iterator<Integer> swap = swaps.keySet().iterator();
		// for (int i : swaps.keySet()) {
		// 	System.out.println(i);
		// }
		// while (swap.hasNext()) {
		// 	System.out.println(swap.next());
		// }

		// for (Integer i: swapIds) {
		// 	System.out.println(i);
		// }

		// Collections.shuffle(swapIds);

		// int[] arr = {0, 1, 2, 3, 4, 5};
		// HashSet<Integer> myset = new HashSet<>(Arrays.asList(arr));





		// for (int i = 0; i < dim; i++) {
		// 	for (int j = 0; j < dim; j++) {
		// 		System.out.print(matrixD[i][j] + "\t");
		// 	}
		// 	System.out.println();
		// }
		int[] atour = {0, 3, 2, 1};
		// printTour(tour);

		// int[] btour = Arrays.copyOf(atour, atour.length);


		// newTour(btour, 2);

		// printTour(atour);
		// printTour(btour);
		// 


		// printTour(tour);
		// shuffle(tour);
		// printTour(tour);
		// this.currentCost = tourLength(tour);
		hillClimbingRepeat(tour);
		// printTour(tour);



	}


	private void printTour(int[] tour){
		for (int i = 0; i < tour.length; i++) {
			System.out.print(tour[i] + " ");
		}
		System.out.println();
	}


	private double dist(int i , int j) {
        double dx = nodes[i][0] - nodes[j][0];
        double dy = nodes[i][1] - nodes[j][1];
        return Math.sqrt(dx * dx + dy * dy);
	}


	private double tourLength(int[] tour) {
		double cost = 0;
		for (int i = 0; i < tour.length - 1; i++) {
			cost += matrixD[tour[i]][tour[i+1]];
		}
		cost+= matrixD[tour[tour.length-1]][tour[0]];
		return cost;
	}


	private int[] newTour(int[] tour, int id) {

		int[] swap = swaps.get(id);
		int i = swap[0];
		int j = swap[1];

		int temp = tour[i];
		tour[i] = tour[j];
		tour[j] = temp;

		return tour;

		//generate a new tour
		//
		//
		//Have a list list all possible swap of indices
		//have a set 
		//return a new tour
	}


	private int[] hillClimbing(int[] tour) {
        //evaluate current tour cost
        //generate new tours based on current
        //compare
        double localMin = tourLength(tour);
//        int[] sol = tour;
        double newCost;
        int iter = totalIter;

        /*
        int iterator = 0;
        while (iterator < 5000) {
            int[] newTour = twoOpt(tour);
            newCost = tourLength(newTour);
            if (newCost < currentCost) {
                sol = newTour;
                currentCost = newCost;
            }
            iterator ++;

        }
        return sol;

        */


        HashSet<Integer> swapped = new HashSet<>();

        // System.out.println("Climbing " + iter);
        while (iter < numIteration) {
            //Calculate the cost of current tour
            // currentCost = tourLength(tour);
//			int[] newTour = Arrays.copyOf(tour, tour.length);

            int swap = randy.nextInt(swaps.size());
            if (!swapped.contains((swap))) {
                swapped.add(swap);
//				newTour(newTour, swap);
                int[] newTour = twoOpt(tour, swap);
                newCost = tourLength(newTour);
                iter++;//Made one swap
                if (newCost < localMin) {
                    //If find a better one, start over from current solution

                    tour = newTour;
                    localMin = newCost;
                    swapped = new HashSet<>();
                    // System.out.print("Startover + ");
                    System.out.println("iter: " + (iter - 1) + " " + newCost);
                }
            }


            if (swapped.size() == swaps.size()) {
                // System.out.println("All done " + iter);
                break;
            }
        }
		totalIter = iter;
		// System.out.println(iter + " " + numIteration +  " " + currentCost);
		return tour;

	}


	private int[] hillClimbingRepeat(int[] tour) {
		// int iter = 0;
		double a, b, c;
		printTour(tour);
		System.out.println("Here");
		shuffle(tour);


		a = currentCost;
		printTour(tour);
		System.out.println("Before climbing cost: " + currentCost);
		tour = hillClimbing(tour);
        this.currentCost = tourLength(tour);
		System.out.println("Here AGAIN");
		System.out.println("After climbing cost: " + currentCost);
		printTour(tour);
		b = currentCost;
		System.out.println("Second climb");
		while (totalIter < numIteration) {
			// System.out.println("Repeating " + totalIter);
			totalIter++;
			if (totalIter %10000 == 1) {
				System.out.println("Repeating" + totalIter);
			}
//			int[] newTour = Arrays.copyOf(tour, tour.length);
			// System.out.println("newTour");
			// printTour(newTour);
			// shuffle(newTour);
//			newTour = newTour(newTour, randy.nextInt(swaps.size()));
//			newTour = newTour(newTour, randy.nextInt(swaps.size()));
            int[] newTour = doubleBridgeMove(tour);
			// System.out.println("Nww  shuffled");
			// printTour(newTour);
			newTour = hillClimbing(newTour);
            if (tourLength(newTour) < tourLength(tour)) {
                tour = newTour;
                this.currentCost = tourLength(tour);
            }
			// printTour(tour);
		}

		System.out.println("After while tour");
		printTour(tour);
		System.out.println(currentCost);

		c = currentCost;
		System.out.println(a + " " + b + " " + c);
		printTour(tour);
		return tour;
	}

	private void shuffle(int[] ar) {
    	for (int i = ar.length - 1; i > 0; i--) {
      		int index = randy.nextInt(i + 1);
      // Simple swap
      		int a = ar[index];
      		ar[index] = ar[i];
      		ar[i] = a;
   		}
  	}

    // Implement double bridge move to create a perturbation
    private int[] doubleBridgeMove(int[] sol) {
        int[] perturbed = new int[sol.length];
        int cut1 = 1 + randy.nextInt(sol.length / 4);
        int cut2 = 1 + cut1 + randy.nextInt(sol.length / 4);
        int cut3 = 1 + cut2 + randy.nextInt(sol.length/ 4);
        System.arraycopy(sol, 0, perturbed, 0, cut1);
        System.arraycopy(sol, cut3, perturbed, cut1, sol.length - cut3);
        System.arraycopy(sol, cut2, perturbed, cut1 + sol.length - cut3, cut3 - cut2);
        System.arraycopy(sol, cut1, perturbed, cut1 + sol.length - cut2, cut2 - cut1);
        return perturbed;
    }

    private int[] threeBridgeMove(int[] sol) {
        int[] perturbed = new int[sol.length];
        int cut1 = 1 + randy.nextInt(sol.length / 5);
        int cut2 = 1 + cut1 + randy.nextInt(sol.length / 5);
        int cut3 = 1 + cut2 + randy.nextInt(sol.length/ 5);
        int cut4 = 1 + cut3 + randy.nextInt(sol.length / 5);
        System.arraycopy(sol, 0, perturbed, 0, cut1);
        System.arraycopy(sol, cut4, perturbed, cut1, sol.length - cut4);
        System.arraycopy(sol, cut2, perturbed, cut1 + sol.length - cut4, cut3 - cut2);
        System.arraycopy(sol, cut1, perturbed, cut1 + sol.length - cut2, cut2 - cut1);
        System.arraycopy(sol, cut1, perturbed, cut1 + sol.length - cut2, cut2 - cut1);

        return perturbed;
    }
 	// private int[] shuffle() {
 	// 	newArr = new int[]
 	// }

    private int[] twoOpt(int[] candidate, int swapId) {
        int length = candidate.length;
        int[] output = new int[length];
//        int cut1 = 1 + (int) (Math.random() * (length / 3));
//        int cut2 = 1 + (int) (Math.random() * (length / 3)) + cut1;
        int cut1 = swaps.get(swapId)[0];
        int cut2 = swaps.get(swapId)[1];
        System.arraycopy(candidate, 0, output, 0, cut1);
        int[] middle = new int[cut2 - cut1];
        System.arraycopy(candidate, cut1, middle, 0, cut2 - cut1);
        for (int i = 0; i < cut2 - cut1; i ++) {
            middle[i] = candidate[cut2 - i - 1];
        }
        System.arraycopy(middle, 0, output, cut1, cut2 - cut1);
        System.arraycopy(candidate, cut2, output, cut2, candidate.length - cut2);
        return output;
    }


	public static void main(String[] args) throws IOException{

		new HillClimbing("./DATA/Boston.tsp");
	}
}