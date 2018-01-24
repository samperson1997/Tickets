package tickets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tickets.bean.ResultMessageBean;
import tickets.bean.VenueMiniBean;
import tickets.service.ManagerService;

import java.util.List;

@Controller
@RequestMapping("/managers")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @RequestMapping(
            value = "/uncheckedVenues",
            method = RequestMethod.GET
    )
    @ResponseBody
    public List<VenueMiniBean> getUncheckedVenues() {

        return managerService.getUncheckedVenues();
    }

    @RequestMapping(
            value = "/checkVenue",
            method = RequestMethod.POST
    )
    @ResponseBody
    public ResultMessageBean checkedVenue(String id, int result) {

        return managerService.checkVenue(id, result);
    }
}
