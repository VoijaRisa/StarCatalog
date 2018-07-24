package StarCatalog.controllers;

import StarCatalog.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping("star")
public class StarController {

    // Index
    @RequestMapping("")
    public String index(Model model) {

        ArrayList<Star> stars = StarData.getAll();
        ArrayList<Observation> observations = ObservationData.getAll();
        ArrayList<Location> locations = LocationData.getAll();

        model.addAttribute("stars", stars);
        model.addAttribute("observations", observations);
        model.addAttribute("locations", locations);
        model.addAttribute("title", "Uranometria 2.0");

        return "star/index";
    }

    // Add a Star
    @RequestMapping(value = "addstar", method = RequestMethod.GET)
    public String displayAddStarForm(Model model) {

        model.addAttribute(new Star());
        model.addAttribute("title", "Add Star");

        return "star/addstar";
    }

    @RequestMapping(value = "addstar", method = RequestMethod.POST)
    public String processAddStarForm(@ModelAttribute @Valid Star newStar, Error errors, Model model) {
        StarData.add(newStar);
        return "redirect:";
    }

    // Add an Observation
    @RequestMapping(value = "addobservation", method = RequestMethod.GET)
    public String displayAddObservationForm(Model model) {
        model.addAttribute(new Observation());
        model.addAttribute("stars", StarData.getAll());
        model.addAttribute("locations", LocationData.getAll());
        model.addAttribute("title", "Add Observation");

        return "star/addobservation";
    }

    @RequestMapping(value = "addobservation", method = RequestMethod.POST)
    public String processAddObservationForm(@ModelAttribute @Valid Observation newObservation, Error errors, Model model) {
        newObservation.setSiderealTimeDeg();
        newObservation.setLatitude();
        newObservation.setDec();
        newObservation.setRA();

        ObservationData.add(newObservation);

        // Calculates updated average for RA and assigns to the submitted star
        ArrayList<Star> stars = StarData.getAll();
        ArrayList<Observation> observations = ObservationData.getAll();

        Double sum = 0.0;
        Integer counter = 0;
        Double RAaverage = 0.0;

        for (Observation observation : observations) {
            Integer objectId = observation.getObjectId();
            Integer newObjectId = newObservation.getObjectId();
            if (objectId.equals(newObjectId)) {
                sum += observation.getRightAscension();
                counter += 1;
                RAaverage = sum/counter;
                }
            }
        for (Star star : stars) {
            if (star.getStarId() == (newObservation.getObjectId())) {
                star.setAvgRA(RAaverage);
            }
        }

        // Calculates updated stDev for RA and assigns to the submitted star
        Double RAstDev = 0.0;
        Double RAstSum = 0.0;

        for (Observation observation : observations) {
            for (Star star : stars) {
                if (star.getStarId() == newObservation.getObjectId()) {
                    Double xMinX = observation.getRightAscension() - RAaverage;
                    xMinX = xMinX * xMinX;
                    RAstSum = RAstSum + xMinX;
                }
            }
        }

        RAstSum = RAstSum/counter;
        RAstSum = Math.sqrt(RAstSum);

        for (Star star : stars) {
            if (star.getStarId() == newObservation.getObjectId()) {
                star.setStDevRA(RAstSum);
            }
        }

        // Calculates updated average for Dec and assigns to the submitted star

        Double decSum = 0.0;
        Integer decCounter = 0;
        Double decAverage = 0.0;

        for (Observation observation : observations) {
            Integer objectId = observation.getObjectId();
            Integer newObjectId = newObservation.getObjectId();
            if (objectId.equals(newObjectId)) {
                decSum += observation.getDeclination();
                decCounter += 1;
                decAverage = decSum/decCounter;
            }
        }
        for (Star star : stars) {
            if (star.getStarId() == (newObservation.getObjectId())) {
                star.setAvgDec(decAverage);
            }
        }

        // Calculates updated stDev for RA and assigns to the submitted star
        Double decStDev = 0.0;
        Double decStSum = 0.0;

        for (Observation observation : observations) {
            Double xMinX = observation.getDeclination() - decAverage;
            xMinX = xMinX * xMinX;
            decStSum = decStSum + xMinX;
        }

        decStSum = decStSum/decCounter;
        decStSum = Math.sqrt(decStSum);

        for (Star star : stars) {
            if (star.getStarId() == newObservation.getObjectId()) {
                star.setStDevDec(decStSum);
            }
        }

        // Redirects back to root
        return "redirect:";
    }

    // View Graph
    @RequestMapping(value = "chart", method = RequestMethod.GET)
    public String chart(Model model) {
        model.addAttribute("title", "Star Chart");

        ArrayList<Star> stars = StarData.getAll();

        // get X values
        String xs = "";
        for (Star star : stars) {
            double value = star.getAvgRA();
            xs = xs + value + ", ";
        }

        // get X errors
        String xError = "";
        for (Star star : stars) {
            double value = star.getStDevRA();
            xError = xError + value + ", ";
        }

        // get Y values
        String ys = "";
        for (Star star : stars) {
            double value = star.getAvgDec();
            ys = ys + value + ", ";
        }

        // get Y errors
        String yError = "";
        for (Star star : stars) {
            double value = star.getStDevDec();
            yError = yError + value + ", ";
        }

        model.addAttribute("xs", xs);
        model.addAttribute("xError", xError);
        model.addAttribute("ys", ys );
        model.addAttribute("yError", yError);

        return "star/chart";
    }
}
