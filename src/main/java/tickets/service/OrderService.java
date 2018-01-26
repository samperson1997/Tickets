package tickets.service;

import tickets.bean.OrderBean;
import tickets.bean.ResultMessageBean;

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
    OrderBean getOrderByOrderId (String orderId);
}
