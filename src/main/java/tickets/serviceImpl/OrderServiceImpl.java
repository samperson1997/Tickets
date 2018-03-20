package tickets.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tickets.bean.*;
import tickets.dao.OrderDao;
import tickets.dao.PlanDao;
import tickets.dao.UserDao;
import tickets.dao.VenueDao;
import tickets.model.*;
import tickets.service.OrderService;
import tickets.util.MemberLevelUtil;
import tickets.util.OrderStateUtil;

import java.time.LocalDateTime;
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

    @Autowired
    UserDao userDao;

    private OrderStateUtil orderStateUtil = new OrderStateUtil();
    private MemberLevelUtil memberLevelUtil = new MemberLevelUtil();


    @Override
    public String addOrder(OrderBean orderBean) {
        String orderId = getOrderId();
        Order order = new Order(Integer.valueOf(orderId), orderBean.getEmail(),
                orderBean.getPlanId(), orderBean.getPrice(), orderBean.getRealPrice(),
                0, orderBean.getIsSeatSelected(), 0, 0, 0, 0,
                orderBean.getSeatName(), orderBean.getSeatNum(), orderBean.getSeatAssigned());
        orderDao.saveOrUpdateOrder(order);

        return orderId;
    }

    @Override
    public String addOrderOnScene(OrderBean orderBean) {

        List<PlanSeat> planSeats = planDao.getPlanSeat(orderBean.getPlanId());

        String seatName = orderBean.getSeatName();
        StringBuilder seatAssigned = new StringBuilder(); // 分配到的座位号
        for (PlanSeat planSeat : planSeats) {

            if (planSeat.getName().equals(seatName)) {

                String seats = planSeat.getSeats(); // 剩下的座位号
                for (int i = 0; i < orderBean.getSeatNum(); i++) {
                    seatAssigned.append(seats.split(";")[0]).append(";");
                    seats = seats.substring(seats.indexOf(";") + 1);
                }

                Order order = new Order(Integer.valueOf(getOrderId()), orderBean.getEmail(),
                        orderBean.getPlanId(), orderBean.getPrice(), orderBean.getRealPrice(),
                        1, 1, 1, 1, 0, 0,
                        orderBean.getSeatName(), orderBean.getSeatNum(), seatAssigned.toString());

                orderDao.saveOrUpdateOrder(order);

                planDao.updatePlanSeat(orderBean.getPlanId(), seatName, -orderBean.getSeatNum());
                planDao.updatePlanSeat(orderBean.getPlanId(), seatName, seats);
                break;
            }
        }

        String seatAssignedString = seatAssigned.toString();
        return seatAssignedString.substring(0, seatAssignedString.lastIndexOf(";"));
    }

    @Override
    public String addOrderWithinTwoWeeks(OrderBean orderBean) {
        List<PlanSeat> planSeats = planDao.getPlanSeat(orderBean.getPlanId());

        double highPrice = planSeats.get(0).getPrice();
        for (PlanSeat planSeat : planSeats) {
            highPrice = (planSeat.getPrice() > highPrice) ? planSeat.getPrice() : highPrice;
        }

        StringBuilder seatAssigned = new StringBuilder(); // 分配到的座位号
        if (orderBean.getIsSeatSelected() == 1) {
            String seatName = orderBean.getSeatName();

            for (PlanSeat planSeat : planSeats) {

                if (planSeat.getName().equals(seatName)) {

                    String seats = planSeat.getSeats(); // 剩下的座位号
                    for (int i = 0; i < orderBean.getSeatNum(); i++) {
                        seatAssigned.append(seats.split(";")[0]).append(";");
                        seats = seats.substring(seats.indexOf(";") + 1);
                    }

                    Order order = orderDao.getOrderByOrderId(Integer.valueOf(orderBean.getOrderId()));
                    order.setIsAssigned(1);
                    order.setSeatAssigned(seatAssigned.toString());

                    orderDao.saveOrUpdateOrder(order);
                    planDao.updatePlanSeat(orderBean.getPlanId(), seatName, seats);
                    break;
                }
            }
        }

        // 未选座购票
        else if (orderBean.getIsSeatSelected() == 0) {
            int seatNum = orderBean.getSeatNum();
            boolean isAssigned = false;

            for (PlanSeat planSeat : planSeats) {
                String seats = planSeat.getSeats(); // 剩下的座位号
                // 先看座位够不够
                int remainSeatNum = seats.split(";").length;
                if (remainSeatNum < seatNum) {
                    continue;
                }

                isAssigned = true;
                for (int i = 0; i < seatNum; i++) {
                    seatAssigned.append(seats.split(";")[0]).append(";");
                    seats = seats.substring(seats.indexOf(";") + 1);
                }

                User user = userDao.getUser(orderBean.getEmail());
                double discount = memberLevelUtil.getLevelDiscount(user.getScore());
                double totalPrice = highPrice * seatNum * discount;
                double couponPrice = totalPrice - orderBean.getPrice();
                double realPrice = planSeat.getPrice() * seatNum * discount - couponPrice;

                Order order = orderDao.getOrderByOrderId(Integer.valueOf(orderBean.getOrderId()));
                order.setIsAssigned(1);
                order.setSeatName(planSeat.getName());
                order.setSeatAssigned(seatAssigned.toString());
                order.setRealPrice(realPrice);
                orderDao.saveOrUpdateOrder(order);

                planDao.updatePlanSeat(orderBean.getPlanId(), planSeat.getName(), seats);
                planDao.updatePlanSeat(orderBean.getPlanId(), planSeat.getName(), -orderBean.getSeatNum());

                double returnPrice = order.getPrice() - realPrice;
                user.setAccount(user.getAccount() + returnPrice);
                userDao.saveOrUpdateUser(user);

                break;
            }

            if (!isAssigned) {
                // 配票失败，全额退款
                User user = userDao.getUser(orderBean.getEmail());
                user.setAccount(user.getAccount() + orderBean.getPrice());
                userDao.saveOrUpdateUser(user);

                Order order = orderDao.getOrderByOrderId(Integer.valueOf(orderBean.getOrderId()));

                order.setIsClosed(1);
                order.setRealPrice(0);
                orderDao.saveOrUpdateOrder(order);
            }
        }

        String seatAssignedString = seatAssigned.toString();
        if (!seatAssignedString.equals("")) {
            seatAssignedString = seatAssignedString.substring(0, seatAssigned.lastIndexOf(";"));
        }

        return seatAssignedString;

    }

    @Override
    public ResultMessageBean updateOrder(OrderBean orderBean) {
        Order order = orderDao.getOrderByOrderId(Integer.valueOf(orderBean.getOrderId()));
        order = orderStateUtil.updateOrderState(orderBean, order);
        order.setRealPrice(orderBean.getRealPrice());

        orderDao.saveOrUpdateOrder(order);
        return new ResultMessageBean(true);
    }

    @Override
    public OrderBean getOrderByOrderId(String orderId) {
        Order order = orderDao.getOrderByOrderId(Integer.valueOf(orderId));
        if (order != null) {
            String state = orderStateUtil.getOrderState(order.getIsPaid(), order.getIsSeatSelected(), order.getIsAssigned(),
                    order.getIsUsed(), order.getIsClosed(), order.getIsOut());

            String seatAssigned = order.getSeatAssigned();
            if (!seatAssigned.equals("")) {
                seatAssigned = seatAssigned.substring(0, seatAssigned.lastIndexOf(";"));
            }

            return new OrderBean(orderId, order.getEmail(), order.getPlanId(), order.getPrice(), order.getRealPrice(),
                    order.getIsSeatSelected(), state, order.getSeatName(), order.getSeatNum(), seatAssigned);

        } else {
            return null;
        }
    }

    @Override
    public List<OrderPlanBean> getOrderByEmail(String email) {
        List<Order> orders = orderDao.getOrderByEmail(email);
        List<OrderPlanBean> res = new ArrayList<>();
        for (Order order : orders) {
            String state = orderStateUtil.getOrderState(order.getIsPaid(), order.getIsSeatSelected(), order.getIsAssigned(),
                    order.getIsUsed(), order.getIsClosed(), order.getIsOut());
            Plan plan = planDao.getPlanByPlanId(order.getPlanId());
            Venue venue = venueDao.getVenueById(plan.getVenueId());

            String seatAssigned = order.getSeatAssigned();
            if (!seatAssigned.equals("")) {
                seatAssigned = seatAssigned.substring(0, seatAssigned.lastIndexOf(";"));
            }
            res.add(new OrderPlanBean(getOrderId(order.getOrderId()), order.getPrice(), order.getRealPrice(),
                    order.getIsSeatSelected(), state, order.getSeatName(), order.getSeatNum(), seatAssigned,
                    venue.getName(), venue.getLocation(), String.valueOf(plan.getStartTime()),
                    String.valueOf(plan.getEndTime()), plan.getType(), plan.getIntroduction()));
        }
        return res;
    }

    @Override
    public UserStatisticBean getMemberStatistic(String email) {
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

            totalPrice += order.getRealPrice();
        }
        return new UserStatisticBean(allOrders, closedOrders, cancelOrders, totalPrice,
                type1Order, type2Order, type3Order, type4Order, type5Order);
    }

    @Override
    public VenueStatisticBean getVenueStatistic(String venueId) {

        List<Plan> plans = planDao.getPlansByVenueId(venueId);

        int allOrders = 0;
        int cancelOrders = 0;
        double totalPrice = 0;

        int planNum = plans.size();
        int endPlanNum = 0;

        for (Plan plan : plans) {
            if (plan.getEndTime().isBefore(LocalDateTime.now())) {
                endPlanNum++;
            }

            List<Order> orders = orderDao.getOrderByPlanId(plan.getId());
            allOrders += orders.size();

            for (Order order : orders) {
                if (order.getIsClosed() == 1 && order.getIsPaid() == 1) {
                    cancelOrders++;
                }

                totalPrice += order.getRealPrice();
            }
        }
        return new VenueStatisticBean(planNum, endPlanNum, allOrders, cancelOrders, totalPrice,
                venueDao.getVenueById(venueId).getAccount());
    }

    @Override
    public ResultMessageBean checkValidation(String orderId, String venueId) {
        if (orderId.length() != 7) {
            return new ResultMessageBean(false, "取票号错误");
        }

        OrderBean orderBean = getOrderByOrderId(orderId);
        if (orderBean == null) {
            return new ResultMessageBean(false, "取票号错误");
        } else {
            String venue = planDao.getPlanByPlanId(orderBean.getPlanId()).getVenueId();
            if (!venue.equals(venueId)) {
                return new ResultMessageBean(false, "取票号错误");
            } else if (orderBean.getState().equals("已关闭")) {
                return new ResultMessageBean(false, "该订单已取消");
            } else if (orderBean.getState().equals("已使用")) {
                return new ResultMessageBean(false, "该订单已使用");
            }
        }
        return new ResultMessageBean(true);
    }

    @Override
    public void assignTickets(int planId) {

        List<Order> orders = orderDao.getOrderByPlanId(planId);

        for (Order order : orders) {
            // 选座购票
            if (order.getIsClosed() == 0 && order.getIsSeatSelected() == 1 && order.getIsAssigned() == 0) {
                List<PlanSeat> planSeats = planDao.getPlanSeat(planId);
                String seatName = order.getSeatName();
                for (PlanSeat planSeat : planSeats) {
                    if (planSeat.getName().equals(seatName)) {
                        StringBuilder seatAssigned = new StringBuilder(); // 分配到的座位号
                        String seats = planSeat.getSeats(); // 剩下的座位号
                        for (int i = 0; i < order.getSeatNum(); i++) {
                            seatAssigned.append(seats.split(";")[0]).append(";");
                            seats = seats.substring(seats.indexOf(";") + 1);
                        }

                        order.setSeatAssigned(seatAssigned.toString());
                        order.setIsAssigned(1);
                        orderDao.saveOrUpdateOrder(order);

                        planDao.updatePlanSeat(planId, seatName, seats);
                        break;
                    }
                }
            }

            // 未选座购票
            if (order.getIsClosed() == 0 && order.getIsSeatSelected() == 0 && order.getIsAssigned() == 0) {
                int seatNum = order.getSeatNum();
                boolean isAssigned = false;

                List<PlanSeat> planSeats = planDao.getPlanSeat(planId);
                double highPrice = planSeats.get(0).getPrice();
                for (PlanSeat planSeat : planSeats) {
                    highPrice = (planSeat.getPrice() > highPrice) ? planSeat.getPrice() : highPrice;
                }

                for (PlanSeat planSeat : planSeats) {
                    String seats = planSeat.getSeats(); // 剩下的座位号
                    // 先看座位够不够
                    int remainSeatNum = seats.split(";").length;
                    if (remainSeatNum < seatNum) {
                        continue;
                    }

                    isAssigned = true;
                    StringBuilder seatAssigned = new StringBuilder(); // 分配到的座位号
                    for (int i = 0; i < seatNum; i++) {
                        seatAssigned.append(seats.split(";")[0]).append(";");
                        seats = seats.substring(seats.indexOf(";") + 1);
                    }


                    User user = userDao.getUser(order.getEmail());
                    double discount = memberLevelUtil.getLevelDiscount(user.getScore());
                    double totalPrice = highPrice * seatNum * discount;
                    double couponPrice = totalPrice - order.getPrice();
                    double realPrice = planSeat.getPrice() * seatNum * discount - couponPrice;

                    order.setSeatName(planSeat.getName());
                    order.setSeatAssigned(seatAssigned.toString());
                    order.setRealPrice(realPrice);
                    order.setIsAssigned(1);
                    orderDao.saveOrUpdateOrder(order);

                    planDao.updatePlanSeat(planId, planSeat.getName(), seats);
                    planDao.updatePlanSeat(order.getPlanId(), planSeat.getName(), -order.getSeatNum());

                    double returnPrice = order.getPrice() - realPrice;
                    user.setAccount(user.getAccount() + returnPrice);
                    userDao.saveOrUpdateUser(user);

                    break;
                }

                if (!isAssigned) {
                    // 配票失败，全额退款
                    User user = userDao.getUser(order.getEmail());
                    user.setAccount(user.getAccount() + order.getPrice());
                    userDao.saveOrUpdateUser(user);

                    order.setIsClosed(1);
                    order.setRealPrice(0);
                    orderDao.saveOrUpdateOrder(order);
                }
            }
        }

        Plan plan = planDao.getPlanByPlanId(planId);
        plan.setIsAssigned(1);
        planDao.saveOrUpdatePlan(plan);
    }

    @Override
    public void closeOrders(int planId) {
        List<Order> orders = orderDao.getOrderByPlanId(planId);
        for (Order order : orders) {
            if (order.getIsUsed() == 0 && order.getIsClosed() == 0) {
                order.setIsOut(1);
                orderDao.saveOrUpdateOrder(order);
            }
        }
    }

    @Override
    public void cancelPlanSeats(OrderBean orderBean) {
        List<PlanSeat> planSeats = planDao.getPlanSeat(orderBean.getPlanId());

        String seatName = orderBean.getSeatName();
        for (PlanSeat planSeat : planSeats) {
            if (planSeat.getName().equals(seatName)) {
                String seatAssigned = orderBean.getSeatAssigned(); // 分配到的座位号
                StringBuilder seats = new StringBuilder(planSeat.getSeats()); // 剩下的座位号
                for (int i = 0; i < orderBean.getSeatNum(); i++) {
                    seats.append(seatAssigned.split(";")[0]).append(";");
                    seatAssigned = seatAssigned.substring(seatAssigned.indexOf(";") + 1);
                }

                Order order = orderDao.getOrderByOrderId(Integer.valueOf(orderBean.getOrderId()));
                order.setSeatAssigned("");
                orderDao.saveOrUpdateOrder(order);

                planDao.updatePlanSeat(orderBean.getPlanId(), seatName, seats.toString());
                break;
            }
        }
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
