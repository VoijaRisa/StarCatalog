package StarCatalog.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class Star {

    private int starId;
    private static int nextId = 1;

    @NotNull
    @Size(min=3, max=25, message="Name out of bounds")
    private String name;

    private Double average = 0.0;

    private Double stDev = 0.0;

    // Constructors
    public Star() {
        starId = nextId;
        nextId++;
    }

    public Star(String aName) {
        name = aName;
    }

    public int getStarId() {
        return starId;
    }

    public void setStarId(int starId) {
        this.starId = starId;
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
}
