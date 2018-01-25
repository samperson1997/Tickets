package tickets.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tickets.bean.PlanSeatBean;
import tickets.bean.PlanVenueBean;
import tickets.bean.ResultMessageBean;
import tickets.service.PlanService;

import java.util.List;

@Controller
@RequestMapping("/plans")
public class PlanController {

    @Autowired
    private PlanService planService;

    @RequestMapping(
            value = "/postPlan",
            method = RequestMethod.POST,
            consumes = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public ResultMessageBean postPlan(@RequestBody PlanSeatBean planSeatBean) {

        return planService.postPlan(planSeatBean);
    }

    @RequestMapping(
            value = "/memberPlan",
            method = RequestMethod.GET
    )
    @ResponseBody
    public List<PlanVenueBean> getMemberPlans(int type) {

        return planService.getMemberPlans(type);
    }
}
