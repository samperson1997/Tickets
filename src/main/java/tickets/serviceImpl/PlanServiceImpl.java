package tickets.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tickets.bean.PlanSeatBean;
import tickets.bean.PlanVenueBean;
import tickets.bean.ResultMessageBean;
import tickets.bean.SeatPriceBean;
import tickets.dao.PlanDao;
import tickets.dao.VenueDao;
import tickets.model.Plan;
import tickets.model.PlanSeat;
import tickets.model.Venue;
import tickets.service.PlanService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlanServiceImpl implements PlanService {

    @Autowired
    PlanDao planDao;

    @Autowired
    VenueDao venueDao;

    @Override
    public ResultMessageBean postPlan(PlanSeatBean planSeatBean) {
        // 存plan表
        int planId = planDao.getPlanNum() + 1;
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime startTime = LocalDateTime.parse(planSeatBean.getStartTime(), df);
        LocalDateTime endTime = LocalDateTime.parse(planSeatBean.getEndTime(), df);

        Plan plan = new Plan(planId, planSeatBean.getVenueId(), startTime,
                endTime, planSeatBean.getType(), planSeatBean.getIntroduction());
        planDao.addPlan(plan);

        // 存planSeat表
        List<SeatPriceBean> seatPriceBeans = planSeatBean.getSeatPriceBeanList();
        for (SeatPriceBean seatPriceBean : seatPriceBeans) {
            planDao.addPlanSeat(new PlanSeat(planId, seatPriceBean.getSeatName(),
                    seatPriceBean.getSeatNum(), seatPriceBean.getSeatPrice()));
        }
        return new ResultMessageBean(true);
    }

    @Override
    public List<PlanVenueBean> getMemberPlans(int type) {
        List<Plan> planList = planDao.getPlan(type);
        List<PlanVenueBean> res = new ArrayList<>();

        for (Plan plan : planList) {
            Venue venue = venueDao.getVenueById(plan.getVenueId());
            List<PlanSeat> planSeats = planDao.getPlanSeat(plan.getId());

            double lowPrice = planSeats.get(0).getPrice();
            for (PlanSeat planSeat : planSeats) {
                lowPrice = (planSeat.getPrice() < lowPrice) ? planSeat.getPrice() : lowPrice;
            }

            res.add(new PlanVenueBean(plan.getId(), plan.getVenueId(), venue.getName(), venue.getLocation(),
                    String.valueOf(plan.getStartTime()), String.valueOf(plan.getEndTime()), plan.getType(),
                    plan.getIntroduction(), lowPrice));
        }

        return res;
    }
}