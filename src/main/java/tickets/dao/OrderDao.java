package tickets.dao;

import tickets.model.Order;

public interface OrderDao {

    /**
     * 添加新订单
     *
     * @param order
     * @return
     */
    boolean saveOrUpdateOrder(Order order);

    /**
     * 获得订单
     *
     * @param orderId
     * @return
     */
    Order getOrderByOrderId(int orderId);

    /**
     * 获得数据库中的订单数
     *
     * @return
     */
    int getOrderNum();
}
