package tickets.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tickets.bean.OrderBean;
import tickets.bean.OrderPlanBean;
import tickets.bean.OrderStatisticBean;
import tickets.bean.ResultMessageBean;
import tickets.dao.OrderDao;
import tickets.dao.PlanDao;
import tickets.dao.VenueDao;
import tickets.model.Order;
import tickets.model.Plan;
import tickets.model.Venue;
import tickets.service.OrderService;
import tickets.util.OrderStateUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    VenueDao venueDao;

    @Autowired
    PlanDao planDao;

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

    @Override
    public List<OrderPlanBean> getOrderByEmail(String email) {
        List<Order> orders = orderDao.getOrderByEmail(email);
        List<OrderPlanBean> res = new ArrayList<>();
        for (Order order : orders) {
            String state = orderStateUtil.getOrderState(order.getIsPaid(), order.getIsSeatSelected(), order.getIsAssigned(),
                    order.getIsUsed(), order.getIsClosed());
            Plan plan = planDao.getPlanByPlanId(order.getPlanId());
            Venue venue = venueDao.getVenueById(plan.getVenueId());

            res.add(new OrderPlanBean(getOrderId(order.getOrderId()), order.getPrice(), order.getRealPrice(),
                    order.getIsSeatSelected(), state, order.getSeatName(), order.getSeatNum(),
                    order.getSeatAssigned(), venue.getName(), venue.getLocation(),
                    String.valueOf(plan.getStartTime()), String.valueOf(plan.getEndTime()),
                    plan.getType(), plan.getIntroduction()));
        }
        return res;
    }

    @Override
    public OrderStatisticBean getOrderStatistic(String email) {
        List<Order> orders = orderDao.getOrderByEmail(email);

        int allOrders = orders.size();
        int closedOrders = 0;
        int cancelOrders = 0;
        double totalPrice = 0;
        int type1Order = 0;
        int type2Order = 0;
        int type3Order = 0;
        int type4Order = 0;
        int type5Order = 0;

        for (Order order : orders) {
            if (order.getIsClosed() == 1) {
                closedOrders++;
                if (order.getIsPaid() == 1) {
                    cancelOrders++;
                }
            } else {
                totalPrice += order.getRealPrice();
                Plan plan = planDao.getPlanByPlanId(order.getPlanId());
                switch (plan.getType()) {
                    case 1:
                        type1Order++;
                        break;
                    case 2:
                        type2Order++;
                        break;
                    case 3:
                        type3Order++;
                        break;
                    case 4:
                        type4Order++;
                        break;
                    case 5:
                        type5Order++;
                        break;
                }
            }
        }
        return new OrderStatisticBean(allOrders, closedOrders, cancelOrders, totalPrice,
                type1Order, type2Order, type3Order, type4Order, type5Order);
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

    /**
     * 获得订单id
     *
     * @param id
     * @return
     */
    private String getOrderId(int id) {
        StringBuilder orderId = new StringBuilder(String.valueOf(id));
        for (int i = orderId.length(); i < 7; i++) {
            orderId.insert(0, "0");
        }
        return orderId.toString();
    }
}
