package StarCatalog.models;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class Observation {

    // Primary Key
    private int observationId;
    private static int nextId = 1;

    // Altitude
    @NotNull
    private Double altitude;

    // Azimuth
    private Double azimuth = 180.0;

    // Date
    @NotNull
    private int year;

    @NotNull
    private int month;

    @NotNull
    private int dayOfMonth;

    private LocalDate date;

    // Location
    private int locationId;

    // Foreign key of Star
    @NotNull
    private Integer objectId;

    // Constructors
    public Observation() {
        observationId = nextId;
        nextId++;
    }

    public Observation(Double aAltitude, int aYear, int aMonth, int aDayOfMonth, int aLocationId, Integer aObjectId) {
        this();
        this.altitude = aAltitude;
        this.year = aYear;
        this.month = aMonth;
        this.dayOfMonth = aDayOfMonth;
        this.locationId = aLocationId;
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

    public Integer getObjectId() {
        return objectId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public void setLocalDate(int year, int month, int dayOfMonth) {
        date = LocalDate.of(year, month, dayOfMonth);
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }
}
