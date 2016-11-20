#!/bin/bash


# graphFiles=`ls ./data/ | grep .gr`
# echo $graphFiles
#    #nospace!
# tspFiles=`ls ./DATA/ | grep .tsp`
# echo $tspFiles

input=Toronto.tsp
input2=Roanoke.tsp
echo $input
echo $input2
# filename=`echo $input | cut -d'.' -f1`
# echo $filename
# 
# 
output=`echo $input | cut -d'.' -f1`
output2=`echo $input2 | cut -d'.' -f1`

# filename=`echo $input | cut -d'.' -f1`
# cut -d "." -f1 $input
# echo $output$'1'

javac src/HillClimbing.java

# for i in `seq 1 10`
# for i in $(seq 1 30)
# do
# 	echo $i$output
# 	java -cp ./src/ HillClimbing ./DATA/$input  ./output/$output$'_'$i.txt

# done

for i in $(seq 22 30)
do
	echo $i$output2
	java -cp ./src/ HillClimbing ./DATA/$input2  ./output/$output2$'_'$i.txt

done

# javac src/RunExperiments.java
# for graph in $('seq 1 10')
# do
# 	filename=`echo $graph | cut -d'.' -f1`
# 	echo $graph $filename
	
# 	#You can change the following line to use your code, then use this file to run all of your experiments. For example, you can execute the python code with:
# 	#python src/RunExperiments.py ./data/$graph ./data/$filename.extra ./results/$filename_output.txt
# 	# java -cp ./src/ RunExperiments ./data/$graph ./data/$filename.extra ./results/$filename$'_output.txt'

# done
