package tickets.service;

import tickets.bean.OrderBean;
import tickets.bean.OrderPlanBean;
import tickets.bean.OrderStatisticBean;
import tickets.bean.ResultMessageBean;

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
    OrderStatisticBean getOrderStatistic(String email);

    /**
     * 查看订单有效性
     *
     * @param orderId
     * @return
     */
    ResultMessageBean checkValidation(String orderId, String venueId);
}
