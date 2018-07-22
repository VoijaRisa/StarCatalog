package StarCatalog.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Location {

    private int locationId;
    private static int nextId = 1;

    @NotNull
    @Size(min=3, max=25, message="Name out of bounds")
    private String location;

    @NotNull(message = "Cannot be blank")
    private Double latitude;

    @NotNull(message = "Cannot be blank")
    private Double longitude;

    // Constructors
    public Location() {
        locationId = nextId;
        nextId++;
    }

    public Location(String aLocation, Double aLatitude, Double aLongitude) {
        location = aLocation;
        latitude = aLatitude;
        longitude = aLongitude;
    }

    // Getters & Setters
    public int getLocationId() {
        return locationId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
