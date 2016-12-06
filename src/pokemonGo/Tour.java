 /**
 * Created by chenzhijian on 11/19/16.
 */
 package pokemonGo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;


public class Tour {
    private int size;
    private ArrayList<Location> locations = new ArrayList<>();

//    public Tour(ArrayList<Location> locations) {
//        this.locations = locations;
//        //Collections.shuffle(this.locations);
//        size = locations.size();
//    }

    public Tour(ArrayList<Location> locations, Random rand) {
        this.locations.addAll(locations);
        Collections.shuffle(this.locations, rand);
        size = this.locations.size();
    }
    public Tour(ArrayList<Location> locations) {
        this.locations.addAll(locations);
        size = this.locations.size();
    }

    public void addLocationToTour(Location n) {
        this.locations.add(n);
    }

    public boolean containsLocation(Location l) {
        return this.locations.contains(l);
    }


    public ArrayList<Location> getLocations() {
        return locations;
    }


    public double getTotalDistance() {
        int size = this.locations.size();
        return this.locations.stream().mapToDouble(x -> {
            int locationIndex = this.locations.indexOf(x);
            double returnValue = 0;
            if (locationIndex < size - 1) {
                returnValue = x.distanceTo(this.locations.get(locationIndex + 1));
            }
            return returnValue;
        }).sum() + this.locations.get(size - 1).distanceTo(this.locations.get(0));
    }

    public String getTotalStringDistance() {
        String returnValue = String.format("%.2f", this.getTotalDistance());
        if (returnValue.length() == 7) {
            returnValue = " " + returnValue;
        }
        return returnValue;
    }

    public int getSize() {
        return this.size;
    }

    public String toString() {
        return Arrays.toString(locations.toArray());
    }

    public void printTour() {
        for (int i = 0; i < locations.size(); i ++) {
            System.out.print(locations.get(i).getId() + ", ");
        }
        System.out.println();

    }
}