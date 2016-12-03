import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

public class Tour {
    private ArrayList<Location> locations = new ArrayList<Location>();

    public Tour(ArrayList<Location> locations, boolean shuffle) {
        this.locations.addAll(locations);
        if (shuffle) {
            Collections.shuffle(this.locations);
        }
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public Tour(Tour route) {
        this.locations.addAll(route.locations);
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

    public String toString() {
        return Arrays.toString(locations.toArray());
    }
}