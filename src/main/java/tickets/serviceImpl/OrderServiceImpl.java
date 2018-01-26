package tickets.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tickets.bean.OrderBean;
import tickets.bean.ResultMessageBean;
import tickets.dao.OrderDao;
import tickets.model.Order;
import tickets.service.OrderService;
import tickets.util.OrderStateUtil;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;

    private OrderStateUtil orderStateUtil = new OrderStateUtil();

    @Override
    public String addOrder(OrderBean orderBean) {
        String orderId = getOrderId();
        Order order = new Order(Integer.valueOf(orderId), orderBean.getEmail(),
                orderBean.getPlanId(), orderBean.getPrice(), orderBean.getRealPrice(),
                0, orderBean.getIsSeatSelected(), 0, 0, 0,
                orderBean.getSeatName(), orderBean.getSeatNum(), orderBean.getSeatAssigned());
        orderDao.saveOrUpdateOrder(order);

        return orderId;
    }

    @Override
    public ResultMessageBean updateOrder(OrderBean orderBean) {
        Order order = orderDao.getOrderByOrderId(Integer.valueOf(orderBean.getOrderId()));
        orderDao.saveOrUpdateOrder(orderStateUtil.updateOrderState(orderBean, order));
        return new ResultMessageBean(true);
    }

    @Override
    public OrderBean getOrderByOrderId(String orderId) {
        Order order = orderDao.getOrderByOrderId(Integer.valueOf(orderId));
        String state = orderStateUtil.getOrderState(order.getIsPaid(), order.getIsSeatSelected(), order.getIsAssigned(),
                order.getIsUsed(), order.getIsClosed());

        return new OrderBean(orderId, order.getEmail(), order.getPlanId(), order.getPrice(), order.getRealPrice(),
                order.getIsSeatSelected(), state, order.getSeatName(), order.getSeatNum(), order.getSeatAssigned());
    }

    /**
     * 获得订单id
     *
     * @return
     */
    private String getOrderId() {
        int orderNum = orderDao.getOrderNum() + 1;
        StringBuilder orderId = new StringBuilder(String.valueOf(orderNum));
        for (int i = orderId.length(); i < 7; i++) {
            orderId.insert(0, "0");
        }
        return orderId.toString();
    }
}
