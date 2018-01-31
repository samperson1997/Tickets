package tickets.service;

import tickets.bean.*;

import java.util.List;

public interface OrderService {

    /**
     * 添加新订单
     *
     * @param orderBean
     * @return orderId
     */
    String addOrder(OrderBean orderBean);

    /**
     * 更新订单
     *
     * @param orderBean
     * @return
     */
    ResultMessageBean updateOrder(OrderBean orderBean);

    /**
     * 获得订单信息
     *
     * @param orderId
     * @return
     */
    OrderBean getOrderByOrderId(String orderId);

    /**
     * 获得用户订单列表
     *
     * @param email
     * @return
     */
    List<OrderPlanBean> getOrderByEmail(String email);

    /**
     * 获得用户订单统计信息
     *
     * @param email
     * @return
     */
    UserStatisticBean getMemberStatistic(String email);

    /**
     * 获得场馆订单统计信息
     *
     * @param venueId
     * @return
     */
    VenueStatisticBean getVenueStatistic(String venueId);

    /**
     * 查看订单有效性
     *
     * @param orderId
     * @return
     */
    ResultMessageBean checkValidation(String orderId, String venueId);

    /**
     * 开票和配票
     *
     * @param planId
     * @return
     */
    void assignTickets(int planId);
}
