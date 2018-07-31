package StarCatalog.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;

@Entity
public class Star {

    @Id
    @GeneratedValue
    private int starId;

    @NotNull
    @Size(min=3, max=25, message="Name out of bounds")
    private String name;

    private double average = 0.0;

    private double stDev = 0.0;

    private double avgRA = 0.0;

    private double stDevRA = 0.0;

    private double avgDec = 0.0;

    private double stDevDec = 0.0;

    // Constructors
    public Star() { }

    public Star(String aName) {
        name = aName;
    }

    // Calculators
    public void setAvgRA() {
        ArrayList<Observation> observations = ObservationData.getAll();

        double sum = 0.0;
        int counter = 0;

        for (Observation observation : observations) {
            if (observation.getObjectId() == starId) {
                sum = sum + observation.getRightAscension();
                counter += 1;
            }
        }

        avgRA = sum/counter;
    }

    public void setAvgDec() {
        ArrayList<Observation> observations = ObservationData.getAll();

        double sum = 0.0;
        int counter = 0;

        for (Observation observation : observations) {
            if (observation.getObjectId() == starId) {
                sum = sum + observation.getDeclination();
                counter += 1;
            }
        }

        avgDec = sum/counter;
    }

    public void setRAStDev() {
        ArrayList<Observation> observations = ObservationData.getAll();

        double sumSquared = 0.0;
        int counter = 0;

        for (Observation observation : observations) {
            if (observation.getObjectId() == starId) {
                sumSquared += (observation.getRightAscension() - avgRA)*(observation.getRightAscension() - avgRA);
                counter += 1;
            }
        }

        if (counter - 1 == 0) {
            stDevRA = 0.0;
        }
        else {
            stDevRA = sumSquared/(counter - 1);
            stDevRA = Math.sqrt(stDevRA);
        }
    }

    public void setDecStDev() {
        ArrayList<Observation> observations = ObservationData.getAll();

        Double sumSquared = 0.0;
        Integer counter = 0;

        for (Observation observation : observations) {
            if (observation.getObjectId() == starId) {
                sumSquared += (observation.getDeclination() - avgDec)*(observation.getDeclination() - avgDec);
                counter += 1;
            }
        }

        if (counter - 1 ==0) {
            stDevDec = 0.0;
        }
        else {
            stDevDec = sumSquared/(counter - 1);
            stDevDec = Math.sqrt(stDevDec);
        }
    }

    public void updateStats() {
        setAvgRA();
        setAvgDec();
        setRAStDev();
        setDecStDev();
    }

    // Getters & Setters

    public int getStarId() {
        return starId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public Double getStDev() {
        return stDev;
    }

    public void setStDev(Double stDev) {
        this.stDev = stDev;
    }

    public Double getAvgRA() {
        return avgRA;
    }

    public Double getStDevRA() {
        return stDevRA;
    }

    public void setStDevRA(Double stDevRA) {
        this.stDevRA = stDevRA;
    }

    public Double getAvgDec() {
        return avgDec;
    }

    public void setAvgDec(Double avgDec) {
        this.avgDec = avgDec;
    }

    public Double getStDevDec() {
        return stDevDec;
    }

    public void setStDevDec(Double stDevDec) {
        this.stDevDec = stDevDec;
    }
}
