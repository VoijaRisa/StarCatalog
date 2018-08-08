package StarCatalog.models;

import StarCatalog.models.Location;
import StarCatalog.models.Star;
import StarCatalog.models.data.LocationDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by LaunchCode
 */
@Entity
public class Observation {

    @Id
    @GeneratedValue
    private int observationId;

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

    // RA
    private Double rightAscension;

    // Dec
    private Double declination;

    // Foreign Key of Star
    @ManyToOne
    private Star star;

    // Foreign Key of Location
    @ManyToOne
    private Location location;

    // Constructor
    public Observation() { }

    //Calculators
    public void setDec(int locationId) {
        Double latitude = getLocation().getLatitude();

        declination = Math.asin((Math.sin(latitude*Math.PI/180)*Math.sin(altitude*Math.PI/180) + Math.cos(latitude*Math.PI/180)*Math.cos(altitude*Math.PI/180)*Math.cos(azimuth*Math.PI/180))) * 180 / Math.PI;
    }

    public void setRA(int locationId) {
        Double latitude = getLocation().getLatitude();

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

    public Star getStar() {
        return star;
    }

    public void setStar(Star star) {
        this.star = star;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
