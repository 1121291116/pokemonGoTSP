# pokemonGoTSP
Comppile Driver.java first.
To run the Driver class, you need to pass in four parameters, which are the directory to the data file, algorithm name, cutoff time, seed and a path for the output files
Algorithm name should be chosen among: LS1, LS2, APP1, APP2, BNB.
LS1: Iterative local search; LS2: Simulated Annealing; APP1: MST Approximation; APP2: Nearest Neighbor; BNB:Branch and Bound
cutoff time is in a unit of second
LS1, LS2 and BNB will need a seed. For the rest algorithms that do not need a seed, you can pass in -1.
