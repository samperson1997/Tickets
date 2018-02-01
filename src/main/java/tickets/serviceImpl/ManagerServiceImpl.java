package tickets.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tickets.bean.ManagerStatisticBean;
import tickets.bean.ResultMessageBean;
import tickets.bean.SeatBean;
import tickets.bean.VenueMiniBean;
import tickets.dao.OrderDao;
import tickets.dao.PlanDao;
import tickets.dao.UserDao;
import tickets.dao.VenueDao;
import tickets.model.*;
import tickets.service.ManagerService;
import tickets.util.MemberLevelUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    VenueDao venueDao;

    @Autowired
    UserDao userDao;

    @Autowired
    PlanDao planDao;

    @Autowired
    OrderDao orderDao;

    private MemberLevelUtil memberLevelUtil = new MemberLevelUtil();

    @Override
    public List<VenueMiniBean> getUncheckedVenues() {
        List<Venue> venueList = venueDao.getUncheckedVenues();
        List<VenueMiniBean> resList = new ArrayList<>();

        for (Venue venue : venueList) {
            List<Seat> seatList = venueDao.getSeat(venue.getId());
            List<SeatBean> seatBeanList = new ArrayList<>();
            for (Seat seat : seatList) {
                seatBeanList.add(new SeatBean(seat.getName(), seat.getNum()));
            }

            VenueMiniBean venueMiniBean = new VenueMiniBean(venue.getId(),
                    venue.getName(), venue.getLocation(), seatBeanList);
            resList.add(venueMiniBean);
        }
        return resList;
    }

    @Override
    public ResultMessageBean checkVenue(String id, int result) {

        StringBuilder realId = new StringBuilder(String.valueOf(id));
        for (int i = realId.length(); i < 7; i++) {
            realId.insert(0, "0");
        }

        Venue venue = venueDao.getVenueById(realId.toString());
        venue.setIsChecked(result);
        venueDao.saveOrUpdateVenue(venue);

        return new ResultMessageBean(true);
    }

    @Override
    public ManagerStatisticBean getManagerStatistic() {

        // 会员
        List<User> userList = userDao.getUserList();
        int userNum = userList.size();
        int[] levelUserNum = {0, 0, 0, 0, 0};
        int[] levelScore = {0, 10000, 30000, 60000, 100000};


        for (User user : userList) {
            for (int i = 0; i < levelScore.length - 1; i++) {
                if (user.getScore() >= levelScore[i] && user.getScore() < levelScore[i + 1]) {
                    levelUserNum[i]++;
                }
            }
            if (user.getScore() >= levelScore[levelScore.length - 1]) {
                levelUserNum[levelScore.length - 1]++;
            }
        }

        // 场馆
        int venueNum = venueDao.getVenueNum();
        int uncheckedVenueNum = 0;
        if (venueDao.getUncheckedVenues() != null) {
            uncheckedVenueNum = venueDao.getUncheckedVenues().size();
        }

        // 活动
        int planNum = planDao.getPlanNum();
        int endPlanNum = 0;
        int[] typePlanNum = {0, 0, 0, 0, 0};

        // 订单
        int allOrders = orderDao.getOrderNum();
        int closedOrders = 0;
        int cancelOrders = 0;
        double totalPrice = 0;


        for (int type = 1; type <= 5; type++) {
            List<Plan> planList = planDao.getPlansByType(type);
            typePlanNum[type - 1] = planList.size();

            for (Plan plan : planList) {
                // endPlanNum
                if (plan.getEndTime().isBefore(LocalDateTime.now())) {
                    endPlanNum++;
                }

                List<Order> orders = orderDao.getOrderByPlanId(plan.getId());

                for (Order order : orders) {
                    // closedOrders
                    if (order.getIsClosed() == 1) {
                        closedOrders++;

                        //cancelOrders
                        if (order.getIsPaid() == 1) {
                            cancelOrders++;
                        }

                        //totalPrice
                    }
                    totalPrice += order.getRealPrice();
                }
            }
        }

        double account = totalPrice / 2;

        return new ManagerStatisticBean(userNum, levelUserNum, venueNum, uncheckedVenueNum,
                planNum, endPlanNum, typePlanNum, allOrders, closedOrders, cancelOrders,
                totalPrice, account);
    }

    @Override
    public ResultMessageBean charge() {
        List<Venue> venueList = venueDao.getVenues();
        for (Venue venue : venueList) {
            List<Plan> planList = planDao.getPlansByVenueId(venue.getId());
            double totalPrice = 0;

            for (Plan plan : planList) {
                if (plan.getEndTime().isBefore(LocalDateTime.now()) && plan.getIsCharged() == 0) {
                    List<Order> orders = orderDao.getOrderByPlanId(plan.getId());
                    for (Order order : orders) {
                        totalPrice += order.getRealPrice();
                    }
                    plan.setIsCharged(1);
                    planDao.saveOrUpdatePlan(plan);
                }
            }
            venue.setAccount(venue.getAccount() + totalPrice / 2);
            venueDao.saveOrUpdateVenue(venue);
        }
        return new ResultMessageBean(true);
    }
}
