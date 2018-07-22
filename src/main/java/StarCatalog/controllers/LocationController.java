package StarCatalog.controllers;

import StarCatalog.models.Location;
import StarCatalog.models.LocationData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping("location")
public class LocationController {

    // Index
    @RequestMapping("")
    public String index(Model model) {
        ArrayList<Location>  locations = LocationData.getAll();

        model.addAttribute("locations", LocationData.getAll());
        model.addAttribute("title", "Locations");

        return "location/index";
    }

    // Add Location
    @RequestMapping(value = "addlocation", method = RequestMethod.GET)
    public String displayAddLocation(Model model) {

        model.addAttribute(new Location());
        model.addAttribute("title", "Add Location");

        return "location/addlocation";
    }

    @RequestMapping(value = "addlocation", method = RequestMethod.POST)
    public String processAddLocation(@ModelAttribute @Valid Location newLocation, Error errors, Model model) {
        LocationData.add(newLocation);
        return "redirect:";
    }
}
