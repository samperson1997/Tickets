package tickets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tickets.bean.ResultMessageBean;
import tickets.bean.SeatBean;
import tickets.bean.VenueBean;
import tickets.bean.VenueSeatBean;
import tickets.service.VenueService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/venue")
public class VenueController {

    @Autowired
    private VenueService venueService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessageBean login(HttpServletRequest request, String id, String password) {

        return venueService.login(id, password);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessageBean register(String id, String password, String location) {

        return venueService.register(id, password, location);
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public VenueBean getVenueInfo(String id) {

        return venueService.getVenue(id);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessageBean updateVenueInfo(String id, VenueBean venueBean) {
        return venueService.updateVenueInfo(id, venueBean);
    }

    @RequestMapping(value = "/seat", method = RequestMethod.GET)
    @ResponseBody
    public List<SeatBean> getSeat(String id) {
        return venueService.getSeats(id);
    }

    @ResponseBody
    @RequestMapping(value = "/updateSeat",
            method = RequestMethod.POST,
            consumes = {"application/json; charset=UTF-8"}
    )
    public ResultMessageBean updateSeat(@RequestBody VenueSeatBean venueSeatBean) {
        System.out.println(venueSeatBean.getName());
        System.out.println(venueSeatBean.getSeatList());
        return venueService.updateSeatInfo(venueSeatBean.getName(), venueSeatBean.getSeatList());
    }
}
