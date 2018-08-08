package StarCatalog.controllers;

import StarCatalog.models.*;
import StarCatalog.models.data.LocationDao;
import StarCatalog.models.data.ObservationDao;
import StarCatalog.models.data.StarDao;
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

    @Autowired
    private StarDao starDao;

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private ObservationDao observationDao;

    // Index
    @RequestMapping("")
    public String index(Model model) {

        Iterable<Star> stars = starDao.findAll();
        Iterable<Observation> observations = observationDao.findAll();
        Iterable<Location> locations = locationDao.findAll();

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
        starDao.save(newStar);
        return "redirect:";
    }

    // Add an Observation
    @RequestMapping(value = "addobservation", method = RequestMethod.GET)
    public String displayAddObservationForm(Model model) {
        model.addAttribute(new Observation());
        model.addAttribute("stars", starDao.findAll());
        model.addAttribute("locations", locationDao.findAll());
        model.addAttribute("title", "Add Observation");

        return "star/addobservation";
    }

    @RequestMapping(value = "addobservation", method = RequestMethod.POST)
    public String processAddObservationForm(@ModelAttribute @Valid Observation newObservation, Error errors, @RequestParam int starId, @RequestParam int locationId, Model model) {
        // Takes input Sidereal Time and converts to degrees for ease of math
        newObservation.setSiderealTimeDeg();

        // Associates the Observation with the appropriate Star in DB
        Star newStarObservation = starDao.findOne(starId);
        newObservation.setStar(newStarObservation);

        // Associates the Observation with the appropriate Location in DB
        Location newLocationObservation = locationDao.findOne(locationId);
        newObservation.setLocation(newLocationObservation);

        // Calculates RA & Dec and sets them to submitted observation
        newObservation.setDec(locationId);
        newObservation.setRA(locationId);

        // Saves submitted observation to dB
        observationDao.save(newObservation);

//        Iterable<Star> stars = starDao.findAll();

        // Updates stats
//        for (Star star : stars) {
//            if (star.getStarId() == newObservation.getObjectId()) {
//                star.updateStats();
//                break;
//            }
//        }

        // Redirects back to root
        return "redirect:";
    }

    // View Graph
    @RequestMapping(value = "chart", method = RequestMethod.GET)
    public String chart(Model model) {
        model.addAttribute("title", "Star Chart");

        Iterable<Star> stars = starDao.findAll();

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
