package StarCatalog.models;

import StarCatalog.models.Star;

import java.util.ArrayList;

public class StarData {

   static ArrayList<Star> stars = new ArrayList<>();

    // Constructors
    public StarData() {}

    public StarData(Star aStar) {
        this();
        stars.add(aStar);
    }

    // getAll
    public static ArrayList<Star> getAll() {
        return stars;
    }

    // add
    public static void add(Star newStar) {
        stars.add(newStar);
    }

    // remove
    public static void remove(int id) {
        Star starToRemove = getById(id);
        stars.remove(starToRemove);
    }

    // getById
    public static Star getById(int id) {
        Star theStar = null;
        for (Star candidateStar : stars) {
            if (candidateStar.getStarId() == id) {
                theStar = candidateStar;
            }
        }

        return theStar;
    }

}
