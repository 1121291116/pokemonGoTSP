package pokemonGo;

import java.util.HashMap;
import java.util.LinkedList;

public class NearestNeighbor {
	
	private HashMap<Integer, Location> unvisited = new HashMap<Integer, Location>();
	private LinkedList<Integer> TSP = new LinkedList<Integer>();
	
	public NearestNeighbor(HashMap m){
		unvisited.putAll(m);
	}
	
	public LinkedList<Integer> findTSP(){
		int current = 1;
		int minVertex = 0;
		Location cur = unvisited.get(current);
		TSP.add(current);
		unvisited.remove(current);
		while(!unvisited.isEmpty()){
			double minLength = Double.MAX_VALUE;
			for(int i : unvisited.keySet()){
				double curDis = cur.distanceTo(unvisited.get(i));
				if (curDis < minLength){
					minLength = curDis;
					minVertex = i;
				}
			}
			current = minVertex;
			cur = (Location) unvisited.get(current);
			TSP.add(minVertex);
			unvisited.remove(minVertex);
		}
		if(minVertex != 1){
			TSP.add(1);
		}
		return TSP;
	}
	
}
