package StarCatalog.controllers;

import StarCatalog.models.Location;
import StarCatalog.models.data.LocationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.validation.Valid;

@Controller
@RequestMapping("location")
public class LocationController {

    @Autowired
    private LocationDao locationDao;

    // Index
    @RequestMapping("")
    public String index(Model model) {
        Iterable<Location>  locations = locationDao.findAll();

        model.addAttribute("locations", locations);
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
        locationDao.save(newLocation);
        return "redirect:";
    }
}
