# pokemonGoTSP

Our src code contains the following main classes for 5 different algorithms:

Driver: For running different experiments.

BnB: Performs a branch and bound algorithm for finding a optimal solution for TSP problems.

MstApproximation: Perform approximation using MST.

NearestNeighbor: Perform approximation using nearest neighbor.

IterativeLocalSearch: Perform iterative local search for TSP. 

SimulatedAnnealing: Perform simulated annealing for TSP. 

To run experiments:

Compile Driver.java first.

To run the Driver class, you need to pass in 8 parameters, which includes the instance of data file, algorithm name, cutoff time, seed. 

The executable must conform with the following format:

 Driver  -inst < filename >  -alg [BnB|MSTApprox|Heur|LS1|LS2] -time < cutoff in seconds >  -seed < random seed >
 
 For example:
 
 java Driver -inst Atlanta -alg LS1 -time 600 -seed 12345

Algorithm name should be chosen among: LS1, LS2, APP1, APP2, BNB.

LS1: Iterative local search
LS2: Simulated Annealing
 APP1: MST Approximation
 APP2: Nearest Neighbor
 BNB:Branch and Bound
 
Cutoff time is in  unit of second
LS1, LS2 will need a seed 

For the rest algorithms that do not need a seed, you can pass in -1.
