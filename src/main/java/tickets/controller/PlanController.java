package tickets.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tickets.bean.PlanMemberBean;
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

    /**
     * 企业发布计划
     *
     * @param planSeatBean
     * @return
     */
    @RequestMapping(
            value = "/postPlan",
            method = RequestMethod.POST,
            consumes = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public ResultMessageBean postPlan(@RequestBody PlanSeatBean planSeatBean) {

        return planService.postPlan(planSeatBean);
    }

    /**
     * 获得会员计划列表
     *
     * @param type
     * @return
     */
    @RequestMapping(
            value = "/memberPlan",
            method = RequestMethod.GET
    )
    @ResponseBody
    public List<PlanMemberBean> getMemberPlans(int type) {

        return planService.getMemberPlans(type);
    }

    /**
     * 获得企业计划列表
     *
     * @param venueId
     * @return
     */
    @RequestMapping(
            value = "/venuePlan",
            method = RequestMethod.GET
    )
    @ResponseBody
    public List<PlanVenueBean> getVenuePlans(String venueId) {

        return planService.getVenuePlans(venueId);
    }

    /**
     * 获得计划详细情况
     *
     * @param planId
     * @return
     */
    @RequestMapping(
            value = "/detailedPlan",
            method = RequestMethod.GET
    )
    @ResponseBody
    public PlanMemberBean getDetailedPlan(int planId) {

        return planService.getDetailedPlan(planId);
    }
}
