package StarCatalog.models;

import StarCatalog.models.data.LocationDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import java.text.DecimalFormat;

public class Observation {

    @Autowired
    private LocationDao locationDao;

    // Primary Key
    private int observationId;
    private static int nextId = 0;

    // Altitude
    @NotNull
    private Double altitude;

    // Azimuth
    @NotNull
    private Double azimuth;

    // ST
    @NotNull
    private int siderealTimeH;

    @NotNull
    private int siderealTimeM;

    private Double siderealTimeDeg;

    // Lat
    private Double latitude;

    // RA
    private Double rightAscension;

    // Dec
    private Double declination;

     // Foreign key of Location
    @NotNull
    private int locationId;

    // Foreign key of Star
    @NotNull
    private Integer objectId;

    // Constructor
    public Observation() {
        observationId = nextId;
        nextId = nextId + 1;
    }

    // Calculators
    public void setLatitude() {
        latitude = locationDao.findOne(locationId).getLatitude();
    }

    public void setDec() {
        declination = Math.asin((Math.sin(latitude*Math.PI/180)*Math.sin(altitude*Math.PI/180) + Math.cos(latitude*Math.PI/180)*Math.cos(altitude*Math.PI/180)*Math.cos(azimuth*Math.PI/180))) * 180 / Math.PI;
    }

    public void setRA() {
        double sinAlt = Math.sin(altitude*Math.PI/180);
        double sinLat = Math.sin(latitude*Math.PI/180);
        double sinDec = Math.sin(declination*Math.PI/180);

        double cosLat = Math.cos(latitude*Math.PI/180);
        double cosDec = Math.cos(declination*Math.PI/180);

        double preHA = (sinAlt - (sinLat * sinDec))/(cosLat * cosDec);

        if (preHA > 1) {
            preHA = 1;
        }

        double HA = Math.acos(preHA);

        double hourAngle = (Math.sin(altitude*Math.PI/180) - Math.sin(latitude*Math.PI/180)*Math.sin(declination*Math.PI/180))/(Math.cos(latitude*Math.PI/180)*Math.cos(declination*Math.PI/180)) * 180 / Math.PI;

        // Corrects for rounding errors pushing acos > 1 which will be common near meridian
        if (hourAngle > 1) {
            hourAngle= 1;
        }

        hourAngle = Math.acos(hourAngle);

        rightAscension = siderealTimeDeg - hourAngle;
    }

    public void setSiderealTimeDeg() {
        siderealTimeDeg = ((double)siderealTimeH + ((double)siderealTimeM/60))/24*360;
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

    public Double getAzimuth() {
        return azimuth;
    }

    public void setAzimuth(Double azimuth) {
        this.azimuth = azimuth;
    }

    public Double getSiderealTime() {
        return siderealTimeDeg;
    }

    public void setSiderealTime(Double siderealTime) {
        this.siderealTimeDeg = siderealTime;
    }

    public int getSiderealTimeH() {
        return siderealTimeH;
    }

    public void setSiderealTimeH(int siderealTimeH) {
        this.siderealTimeH = siderealTimeH;
    }

    public int getSiderealTimeM() {
        return siderealTimeM;
    }

    public void setSiderealTimeM(int siderealTimeM) {
        this.siderealTimeM = siderealTimeM;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Double getRightAscension() {
        return rightAscension;
    }

    public void setRightAscension(Double rightAscension) {
        this.rightAscension = rightAscension;
    }

    public Double getDeclination() {
        return declination;
    }

    public void setDeclination(Double declination) {
        this.declination = declination;
    }

    public Integer getObjectId() {
        return objectId;
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
