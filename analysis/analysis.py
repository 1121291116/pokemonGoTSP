import csv

city = "Roanoke_"
id = 1;
inFile = city + str(id) + ".txt"

percent = 0.01
optimal = 655454 * (1 + percent)

print(inFile)

ts = []
sol = []
gap = []

for i in range(1, 61):
	gap.append(i * 10)

with open(inFile) as csvfile:
    readCSV = csv.reader(csvfile, delimiter='\t')
    for row in readCSV:
        ts.append(float(row[0]))
        sol.append(float(row[1]))




def find(curr):
	for i in range(len(ts) - 1):
		if ts[i] < curr and ts[i+1] > curr:
			print(ts[i])
			print(sol[i])
			if sol[i] < optimal:
				count[curr] = 1
			else:
				count[curr] = 0
			return


maxTs = max(ts)
count = {}
for i in range(len(gap)):
	curr = gap[i]
	if curr > maxTs:
		count[curr] = count[gap[i-1]]
	else:
		find(curr)




# f = open(inFile, "r")

# data = csv.reader(f),

# for row in data:
# 	print(row);

# f.close()
	# for i in range(len(ts) - 1):
	# 	if ts[i] < curr and ts[i+1] > curr:
	# 		print(ts[i])
	# 		print(sol[i])
	# 		if sol[i] < optimal:
	# 			count[curr] = 1
	# 		else:
	# 			count[curr] = 0
	# 		break

