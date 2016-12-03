/**
 * Created by youssefhammoud on 12/1/16.
 */
import java.util.ArrayList;
import java.util.HashSet;

public class BnB {

    private double[][] adj;
    private double best_dist;
    int[] best_tour;
    double[] penCost;

    /**
     * Constructor
     */
    public BnB(ArrayList<Location> tour) {
        int n = tour.size();
        penCost = new double[n];
        adj = new double[n][n];
        for (int i = 0; i < tour.size(); i++) {
            for (int j = 0; j< tour.size(); j++) {
                if (i != j) {
                    double dist = tour.get(i).distanceTo(tour.get(j));
                    adj[i][j] = dist;
                    if (j != n-1) {
                        System.out.format("%.2f\t", adj[i][j]);
                    } else {
                        System.out.format("%.2f\n", adj[i][j]);
                    }

                } else {
                    adj[i][j] = Double.MAX_VALUE;
                    if (j != n-1) {
                        System.out.print("infinity\t");
                    } else {
                        System.out.print("infinity\n");
                    }
                }
            }
        }

        System.out.println("\n-------------------------------------------------------\n");
    }

    public double rowMin(double[][] adj, int i) {
        double min = adj[i][0];
        for (int j = 0; j < adj.length; j++) {
            if (min > adj[i][j]) {
                min = adj[i][j];
            }
        }
        return min;
    }

    public double colMin(double[][] adj, int i) {
        double min = adj[0][i];
        for (int j = 0; j < adj.length; j++) {
            if (min > adj[j][i]) {
                min = adj[j][i];
            }
        }
        return min;
    }

    public double[][] rowReduction(double[][] adj) {
        double[][] rowReduced = new double[adj.length][adj.length+1];
        for (int i = 0; i < adj.length; i++) {
            double min = adj[0][0];
            for (int j = 0; j < adj.length; j++) {
                if (min > adj[i][j]) {
                    min = adj[i][j];
                }
            }
            for (int j = 0; j <= adj.length; j++) {
                if (j == adj.length) {
                    rowReduced[i][j] = min;
                } else {
                    rowReduced[i][j] = adj[i][j] - min;
                }
            }
            System.out.println();
        }
        System.out.println();

        return rowReduced;
    }

    public double[][] columnReduction(double[][] adj) {

        double[][] columnReduced = new double[adj.length + 1][adj[0].length];

        for (int i = 0; i < adj.length; i++) {

            double min = adj[0][0];
            for (int j = 0; j < adj.length - 1; j++) {

                if (min > adj[j][i]) {
                    min = adj[j][i];
                }
            }

            for (int j = 0; j <= adj.length; j++) {

                if (j != adj.length) {
                    columnReduced[j][i] = adj[j][i] - min;
                } else {
                    columnReduced[j][i] = min;
                }
            }
        }
        int lowerBound = 0;
        for (int k = 0; k < adj.length; k++) {
            columnReduced[k][adj[0].length-1] = adj[k][adj[0].length-1];
            lowerBound += columnReduced[k][adj[0].length-1];
        }
        for (int k = 0; k < adj[0].length - 1; k++) {
            lowerBound += columnReduced[adj.length][k];
        }
        columnReduced[columnReduced.length-1][columnReduced.length-1] = lowerBound;
        return columnReduced;
    }

    public double[][] getAdj() {
        return adj;
    }

    /**
     * Get current path cost
     *
     * @return the best distance
     */
    public double getBest_dist() {
        return best_dist;
    }
}
