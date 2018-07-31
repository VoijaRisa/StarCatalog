package StarCatalog.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Location {

    @Id
    @GeneratedValue
    private int locationId;

    @NotNull
    @Size(min=3, max=25, message="Name out of bounds")
    private String location;

    @NotNull(message = "Cannot be blank")
    private Double latitude;

    @NotNull(message = "Cannot be blank")
    private Double longitude;

    // Constructors
    public Location() { }

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
