package tickets.service;

import tickets.bean.PlanMemberBean;
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
     * 获得会员计划列表
     *
     * @param type
     * @return
     */
    List<PlanMemberBean> getMemberPlans(int type);

    /**
     * 获得企业计划列表
     *
     * @param venueId
     * @return
     */
    List<PlanVenueBean> getVenuePlans(String venueId);

    /**
     * 获得计划详情
     *
     * @param planId
     * @return
     */
    PlanMemberBean getDetailedPlan(int planId);

    /**
     * 更新座位数量
     * seatNum为正表示增加(订单关闭)，为负表示减少(新下订单)
     *
     * @param planId
     * @param seatName
     * @param seatNum
     * @return
     */
    ResultMessageBean updatePlanSeat(int planId, String seatName, int seatNum);
}
