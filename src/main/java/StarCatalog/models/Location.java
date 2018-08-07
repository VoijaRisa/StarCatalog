package StarCatalog.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Location {

    // Setting up variables within model class
    @Id
    @GeneratedValue
    private int locationId;

    @NotNull
    @Size(min=3, max=25)
    private String locationName;

    @NotNull(message = "Cannot be blank")
    private Double latitude;

    @NotNull(message = "Cannot be blank")
    private Double longitude;

    @OneToMany
    @JoinColumn(name = "location_id")
    private List<Observation> observations = new ArrayList<>();

    // Constructors
    public Location(){}

    public Location(String aName) {
        this();
        locationName = aName;
    }

    // Getters and setters for variables
    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int aLocationId) {
        this.locationId = aLocationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
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

    public List<Observation> getObservations() {
        return observations;
    }
}
