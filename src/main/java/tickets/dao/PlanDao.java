package tickets.dao;

import tickets.model.Plan;
import tickets.model.PlanSeat;

import java.util.List;

public interface PlanDao {

    /**
     * 添加新计划
     *
     * @param plan
     * @return
     */
    boolean saveOrUpdatePlan(Plan plan);

    /**
     * 添加新计划座位
     *
     * @param planSeat
     * @return
     */
    boolean addPlanSeat(PlanSeat planSeat);

    /**
     * 更新座位数量
     *
     * @param planId
     * @param seatName
     * @param seatNum
     * @return
     */
    boolean updatePlanSeat(int planId, String seatName, int seatNum);

    /**
     * 根据类型获得计划列表
     *
     * @param type
     * @return
     */
    List<Plan> getPlansByType(int type);

    /**
     * 根据场馆识别码获得计划列表
     *
     * @param venueId
     * @return
     */
    List<Plan> getPlansByVenueId(String venueId);

    /**
     * 根据计划id获得计划详情
     *
     * @param planId
     * @return
     */
    Plan getPlanByPlanId(int planId);

    /**
     * 根据计划id获得座位
     *
     * @param planId
     * @return
     */
    List<PlanSeat> getPlanSeat(int planId);

    /**
     * 获得数据库中计划数
     *
     * @return
     */
    int getPlanNum();
}
