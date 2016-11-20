import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;

public class HillClimbing {


	String inputFile;
	String outputFile;
	PrintWriter output;
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
	long totalIter;
	long duration;
	long startTime;


	public HillClimbing(String inputFile, String outputFile) throws IOException {
		this.inputFile = inputFile;
		this.outputFile = outputFile;
		this.output = new PrintWriter(outputFile, "UTF-8");
		this.randy = new Random();
		// this.numIteration = 5000000;
		this.totalIter = 0;
		this.duration = 10 * 60 * 1000;
		// long startTime = System.currentTimeMillis();
		run();
		// long endTime = System.currentTimeMillis();
		// System.out.println(startTime + " " + endTime);
		// System.out.println((double)(endTime - startTime) / 1000);

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


        hillClimbingRepeat(tour);
        output.close();
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




	private int[] hillClimbing(int[] tour) {
        //evaluate current tour cost
        //generate new tours based on current
        //compare
        double localMin = tourLength(tour);
        double newCost;
        long iter = System.currentTimeMillis() - startTime;
  		// System.out.println("CURRE" + iter);
  		// System.out.println(duration);




        HashSet<Integer> swapped = new HashSet<>();

        while (System.currentTimeMillis() - startTime < duration) {

            int swap = randy.nextInt(swaps.size());
            if (!swapped.contains((swap))) {
                swapped.add(swap);
                int[] newTour = twoOpt(tour, swap);
                newCost = tourLength(newTour);
                //Made one swap
                if (newCost < localMin) {
                    //If find a better one, start over from current solution

                    tour = newTour;
                    localMin = newCost;
                    swapped = new HashSet<>();
                    // System.out.println("iter: " + (iter - 1) + " " + newCost);
                    if (localMin < currentCost) {
                    	currentCost = localMin;
                    	double ts = ((double)(System.currentTimeMillis() - startTime)) / 1000;
                    	// System.out.println(ts + "\t" + currentCost);
                    	output.format("%.3f\t%f%n", ts, currentCost);
                    }
                }
                // iter = System.currentTimeMillis() - startTime;
                // System.out.println("WEDDDD"  + iter);
            }


            if (swapped.size() == swaps.size()) {
                break;
            }
        }
		totalIter = iter;
		return tour;

	}


	private int[] hillClimbingRepeat(int[] tour) {
		this.startTime = System.currentTimeMillis();
		tour = hillClimbing(tour);
        this.currentCost = tourLength(tour);
        int peak = 0;
		// System.out.println("hllree" + startTime);
		while (totalIter < duration) {
			// totalIter++;
			totalIter = System.currentTimeMillis() - startTime;
			// if (totalIter % 1000 == 1) {
			// 	System.out.println("Repeating" + totalIter);
			// }
            int[] newTour = doubleBridgeMove(tour);
			newTour = hillClimbing(newTour);
            // System.out.println("peak: " + peak);
            // System.out.println("peak height: " + tourLength(newTour));
            if (tourLength(newTour) < tourLength(tour)) {
                tour = newTour;
                this.currentCost = tourLength(tour);
            }
		}

  //       System.out.println("final");
		// System.out.println(currentCost);
  //       printTour(tour);
		return tour;
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



    private int[] twoOpt(int[] candidate, int swapId) {
        int length = candidate.length;
        int[] output = new int[length];
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

		new HillClimbing(args[0], args[1]);
	}
}