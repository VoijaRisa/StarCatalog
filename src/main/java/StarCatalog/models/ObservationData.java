package StarCatalog.models;

import java.util.ArrayList;

public class ObservationData {

    static ArrayList<Observation> observations = new ArrayList<>();

    // Constructors
    public ObservationData() {}

    public ObservationData(Observation aObservation) {
        this();
        observations.add(aObservation);
    }

    // getAll
    public static ArrayList<Observation> getAll() {
        return observations;
    }

    // add
    public static void add(Observation newObservation) {
        observations.add(newObservation);
    }

    // remove
    public static void remove(int id) {
        Observation observationToRemove = getById(id);
        observations.remove(observationToRemove);
    }

    // getById
    public static Observation getById(int id) {
        Observation theObservation = null;

        for (Observation candidateObservation : observations) {
            if (candidateObservation.getObservationId() == id) {
                theObservation = candidateObservation;
            }
        }

        return theObservation;
    }
}
