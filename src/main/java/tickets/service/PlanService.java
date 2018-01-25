package tickets.service;

import tickets.bean.PlanSeatBean;
import tickets.bean.PlanVenueBean;
import tickets.bean.ResultMessageBean;

import java.util.List;

public interface PlanService {

    /**
     * 发布新计划
     *
     * @param planSeatBean
     * @return
     */
    ResultMessageBean postPlan(PlanSeatBean planSeatBean);

    /**
     * 获得计划列表
     *
     * @param type
     * @return
     */
    List<PlanVenueBean> getMemberPlans(int type);
}
