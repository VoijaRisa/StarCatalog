package StarCatalog.models;


import javax.validation.constraints.NotNull;

public class Observation {

    // Primary Key
    private int observationId;
    private static int nextId = 1;

    // Altitude
    @NotNull
    private Double altitude;

    // Date and Time
    @NotNull
    private String daytime;

    // Foreign key of Star
    @NotNull
    private Integer objectId;

    // Constructors
    public Observation() {
        observationId = nextId;
        nextId++;
    }

    public Observation(Double aAltitude, String aDaytime, Integer aObjectId) {
        this();
        this.altitude = aAltitude;
        this.daytime = aDaytime;
        this.objectId = aObjectId;
    }

    // Getters & Setters
    public int getObservationId() {
        return observationId;
    }

    public void setObservationId(int id) {
        this.observationId = id;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public String getDaytime() {
        return daytime;
    }

    public void setDaytime(String daytime) {
        this.daytime = daytime;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }
}
