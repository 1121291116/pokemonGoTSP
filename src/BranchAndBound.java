/**
 * Created by youssefhammoud on 12/1/16.
 */
public class BranchAndBound {

    private int n;
    private int[] final_tour;
    boolean[] visited;
    double final_dist = Double.MAX_VALUE;

    public BranchAndBound(int n) {
        this.n = n;
        final_tour = new int[n+1];
        visited = new boolean[n];
    }

    public void copyToFinal(int curr_path[])
    {
        for (int i = 0; i < n; i++)
            final_tour[i] = curr_path[i];
        final_tour[n] = curr_path[0];
    }

    public double firstMin(double[][] adj, int i)
    {
        double min = Double.MAX_VALUE;
        for (int k=0; k < n; k++)
            if (adj[i][k] < min && i != k)
                min = adj[i][k];
        return min;
    }


    // function to find the second minimum edge cost
    // having an end at the vertex i
    public double secondMin(double[][] adj, int i)
    {
        double first =  Integer.MAX_VALUE, second =  Integer.MAX_VALUE;
        for (int j=0; j<n; j++)
        {
            if (i == j)
                continue;

            if (adj[i][j] <= first)
            {
                second = first;
                first = adj[i][j];
            }
            else if (adj[i][j] <= second &&
                    adj[i][j] != first)
                second = adj[i][j];
        }
        return second;
    }



    public void TSPRec(double[][] adj, double curr_bound, double curr_weight,
                int level, int curr_path[])
    {
        // base case is when we have reached level N which
        // means we have covered all the nodes once
        if (level==n)
        {
            // check if there is an edge from last vertex in
            // path back to the first vertex
            if (adj[curr_path[level-1]][curr_path[0]] != 0)
            {
                // curr_res has the total weight of the
                // solution we got
                double curr_res = curr_weight +
                        adj[curr_path[level-1]][curr_path[0]];

                // Update final result and final path if
                // current result is better.
                if (curr_res < final_dist)
                {
                    copyToFinal(curr_path);
                    final_dist = curr_res;
                }
            }
            return;
        }

        // for any other level iterate for all vertices to
        // build the search space tree recursively
        for (int i=0; i<n; i++)
        {
            // Consider next vertex if it is not same (diagonal
            // entry in adjacency matrix and not visited
            // already)
            if (adj[curr_path[level-1]][i] != 0 &&
                    visited[i] == false)
            {
                double temp = curr_bound;
                curr_weight += adj[curr_path[level-1]][i];

                // different computation of curr_bound for
                // level 2 from the other levels
                if (level==1)
                    curr_bound -= ((firstMin(adj, curr_path[level-1]) +
                            firstMin(adj, i))/2);
                else
                    curr_bound -= ((secondMin(adj, curr_path[level-1]) +
                            firstMin(adj, i))/2);

                // curr_bound + curr_weight is the actual lower bound
                // for the node that we have arrived on
                // If current lower bound < final_res, we need to explore
                // the node further
                if (curr_bound + curr_weight < final_dist)
                {
                    curr_path[level] = i;
                    visited[i] = true;

                    // call TSPRec for the next level
                    TSPRec(adj, curr_bound, curr_weight, level+1,
                            curr_path);
                }

                // Else we have to prune the node by resetting
                // all changes to curr_weight and curr_bound
                curr_weight -= adj[curr_path[level-1]][i];
                curr_bound = temp;

                // Also reset the visited array
                for (int j = 0; i < visited.length; i++) {
                    visited[j] = false;
                }
                for (int j=0; j<=level-1; j++)
                    visited[curr_path[j]] = true;
            }
        }
    }

    public double getFinal_dist() {
        return final_dist;
    }

    public int[] getFinal_tour() {
        return final_tour;
    }

    public void TSP(double[][] adj)
    {
        int[] curr_path = new int[n+1];

        // Calculate initial lower bound for the root node
        // using the formula 1/2 * (sum of first min +
        // second min) for all edges.
        // Also initialize the curr_path and visited array
        int curr_bound = 0;
        for (int i = 0; i < curr_path.length; i++) {
            curr_path[i] = -1;
        }
        for (int i = 0; i < visited.length; i++) {
            visited[i] = false;
        }

        // Compute initial bound
        for (int i=0; i<n; i++)
            curr_bound += (firstMin(adj, i) +
                    secondMin(adj, i));

        // Rounding off the lower bound to an integer
        curr_bound = ((curr_bound & 1) == 1)? ((curr_bound / 2) + 1) :
                (curr_bound / 2);

        // We start at vertex 1 so the first vertex
        // in curr_path[] is 0
        visited[0] = true;
        curr_path[0] = 0;

        // Call to TSPRec for curr_weight equal to
        // 0 and level 1
        TSPRec(adj, curr_bound, 0, 1, curr_path);


    }


}
