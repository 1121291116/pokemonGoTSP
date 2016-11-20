import csv

city = "Roanoke_1.txt"
city2 = "Roanoke_2.txt"
# id = 1;
# inFile = city + str(id) + ".txt"

percent = 0.01
# optimal = 655454 * (1 + percent)

optimal = 658629.103672
# print(inFile)


gap = []

for i in range(1, 61):
	gap.append(i * 10)

countAll = {}





def traverse(ts, sol, curr):
	for i in range(len(ts) - 1):
		if ts[i] < curr and ts[i+1] > curr:
			# print(ts[i])
			# print(sol[i])
			if sol[i] <= optimal:
				return 1
			else:
				return 0

def explore(inFile):
	ts = []
	sol = []
	with open(inFile) as csvfile:
	    readCSV = csv.reader(csvfile, delimiter='\t')
	    for row in readCSV:
	        ts.append(float(row[0]))
	        sol.append(float(row[1]))

	maxTs = max(ts)
	print(maxTs)
	countEach = {}

	maxGap = (maxTs / 10 + 1) * 10
	idx = ts.index(maxTs)
	print(idx)
	print(sol[idx])
	if sol[idx] <= optimal:
		maxSol = 1
	else:
		maxSol = 0

	for i in range(len(gap)):
		curr = gap[i]
		if curr > maxTs:
			countEach[curr] = maxSol;
		else:
			countEach[curr] = traverse(ts, sol, curr)
	return countEach






a = explore(city)
b = explore(city2)



