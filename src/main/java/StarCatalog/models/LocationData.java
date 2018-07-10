package StarCatalog.models;

import java.util.ArrayList;

public class LocationData {
    static ArrayList<Location> locations = new ArrayList<>();

    // Constructors
    public LocationData() {};

    public LocationData(Location aLocation) {
        this();
        locations.add(aLocation);
    }

    // getAll
    public static ArrayList<Location> getAll() {
        return locations;
    }

    // getById
    public static Location getById(int id) {
        Location theLocation = null;
        for (Location candidateLocation : locations) {
            if (candidateLocation.getLocationId() == id) {
                theLocation = candidateLocation;
            }
        }

        return theLocation;
    }

    // add
    public static void add(Location newLocation) {
        locations.add(newLocation);
    }

    // remove
    public static void remove(int id) {
        Location locationToRemove = getById(id);
        locations.remove(locationToRemove);
    }
}
