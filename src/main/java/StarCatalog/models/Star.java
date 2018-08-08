package StarCatalog.models;

import StarCatalog.models.Observation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Star {

    // Setting up variables within model class
    @Id
    @GeneratedValue
    private int starId;

    @NotNull
    @Size(min=3, max=25, message="Name out of bounds")
    private String name;

    private double avgRA = 0.0;

    private double stDevRA = 0.0;

    private double avgDec = 0.0;

    private double stDevDec = 0.0;

    @OneToMany
    @JoinColumn(name = "star_star_id")
    private List<Observation> observations = new ArrayList<>();

    // Constructors
    public Star(){}

    public Star(String aName) {
        this();
        name = aName;
    }

    // Calculators
    public void setAvgRA() {
        List<Observation> observations = getObservations();

        double sum = 0.0;
        int counter = 0;

        for (Observation observation : observations) {
            sum = sum + observation.getRightAscension();
            counter += 1;
        }

        avgRA = sum/counter;
    }

    public void setAvgDec() {
        List<Observation> observations = getObservations();

        double sum = 0.0;
        int counter = 0;

        for (Observation observation : observations) {
            sum = sum + observation.getDeclination();
            counter += 1;
        }

        avgDec = sum/counter;
    }

    public void setRAStDev() {
        List<Observation> observations = getObservations();

        double sumSquared = 0.0;
        int counter = 0;

        for (Observation observation : observations) {
            sumSquared += (observation.getRightAscension() - avgRA)*(observation.getRightAscension() - avgRA);
            counter += 1;
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
        List<Observation> observations = getObservations();

        Double sumSquared = 0.0;
        Integer counter = 0;

        for (Observation observation : observations) {
            sumSquared += (observation.getDeclination() - avgDec)*(observation.getDeclination() - avgDec);
            counter += 1;
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

    // Getters and setters for variables
    public int getStarId() {
        return starId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAvgRA() {
        return avgRA;
    }

    public Double getStDevRA() {
        return stDevRA;
    }

    public Double getAvgDec() {
        return avgDec;
    }

    public Double getStDevDec() {
        return stDevDec;
    }

    public List<Observation> getObservations() {
        return observations;
    }
}
