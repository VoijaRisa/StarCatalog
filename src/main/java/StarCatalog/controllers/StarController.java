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
        newObservation.setLatitude();
        newObservation.setDec();
        newObservation.setRA();

        ObservationData.add(newObservation);

        // Calculates updated average and assigns to the submitted star
        ArrayList<Star> stars = StarData.getAll();
        ArrayList<Observation> observations = ObservationData.getAll();

        Double sum = 0.0;
        Integer counter = 0;
        Double average = 0.0;

        for (Observation observation : observations) {
            Integer objectId = observation.getObjectId();
            Integer newObjectId = newObservation.getObjectId();
            if (objectId.equals(newObjectId)) {
                sum += observation.getAltitude();
                counter += 1;
                average = sum/counter;
                }
            }
        for (Star star : stars) {
            if (star.getStarId() == (newObservation.getObjectId())) {
                star.setAverage(average);
            }
        }

        // Calculates updated stDev and assigns to the submitted star
        Double stDev = 0.0;
        Double stSum = 0.0;

        for (Observation observation : observations) {
            Double xMinX = observation.getAltitude() - average;
            xMinX = xMinX * xMinX;
            stSum = stSum + xMinX;
        }

        stSum = stSum/counter;
        stSum = Math.sqrt(stSum);

        for (Star star : stars) {
            if (star.getStarId() == newObservation.getObjectId()) {
                star.setStDev(stSum);
            }
        }

        // Redirects back to root
        return "redirect:";
    }
}
