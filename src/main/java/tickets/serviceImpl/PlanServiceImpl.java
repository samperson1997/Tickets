package tickets.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tickets.bean.*;
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
    public List<PlanMemberBean> getMemberPlans(int type) {
        List<Plan> planList = planDao.getPlansByType(type);
        List<PlanMemberBean> res = new ArrayList<>();

        for (Plan plan : planList) {
            Venue venue = venueDao.getVenueById(plan.getVenueId());
            List<PlanSeat> planSeats = planDao.getPlanSeat(plan.getId());
            List<SeatPriceBean> seatPriceBeans = new ArrayList<>();

            double lowPrice = planSeats.get(0).getPrice();
            double highPrice = planSeats.get(0).getPrice();
            for (PlanSeat planSeat : planSeats) {
                lowPrice = (planSeat.getPrice() < lowPrice) ? planSeat.getPrice() : lowPrice;
                highPrice = (planSeat.getPrice() > highPrice) ? planSeat.getPrice() : highPrice;
                seatPriceBeans.add(new SeatPriceBean(planSeat.getName(), planSeat.getNumber(), planSeat.getPrice()));
            }

            res.add(new PlanMemberBean(plan.getId(), plan.getVenueId(), venue.getName(), venue.getLocation(),
                    String.valueOf(plan.getStartTime()), String.valueOf(plan.getEndTime()), plan.getType(),
                    plan.getIntroduction(), lowPrice, highPrice, seatPriceBeans));
        }

        return res;
    }

    @Override
    public List<PlanVenueBean> getVenuePlans(String venueId) {
        List<Plan> planList = planDao.getPlansByVenueId(venueId);
        List<PlanVenueBean> res = new ArrayList<>();
        for (Plan plan : planList) {
            List<PlanSeat> planSeats = planDao.getPlanSeat(plan.getId());
            List<SeatPriceBean> seatPriceBeans = new ArrayList<>();

            for (PlanSeat planSeat : planSeats) {
                seatPriceBeans.add(new SeatPriceBean(planSeat.getName(), planSeat.getNumber(), planSeat.getPrice()));
            }

            res.add(new PlanVenueBean(plan.getId(), String.valueOf(plan.getStartTime()),
                    String.valueOf(plan.getEndTime()), plan.getType(), plan.getIntroduction(), seatPriceBeans));
        }
        return res;
    }

    @Override
    public PlanMemberBean getDetailedPlan(int planId) {
        Plan plan = planDao.getPlanByPlanId(planId);
        Venue venue = venueDao.getVenueById(plan.getVenueId());
        List<PlanSeat> planSeats = planDao.getPlanSeat(plan.getId());
        List<SeatPriceBean> seatPriceBeans = new ArrayList<>();

        double lowPrice = planSeats.get(0).getPrice();
        double highPrice = planSeats.get(0).getPrice();
        for (PlanSeat planSeat : planSeats) {
            lowPrice = (planSeat.getPrice() < lowPrice) ? planSeat.getPrice() : lowPrice;
            highPrice = (planSeat.getPrice() > highPrice) ? planSeat.getPrice() : highPrice;
            seatPriceBeans.add(new SeatPriceBean(planSeat.getName(), planSeat.getNumber(), planSeat.getPrice()));
        }

        return new PlanMemberBean(plan.getId(), plan.getVenueId(), venue.getName(), venue.getLocation(),
                String.valueOf(plan.getStartTime()), String.valueOf(plan.getEndTime()), plan.getType(),
                plan.getIntroduction(), lowPrice, highPrice, seatPriceBeans);
    }

    @Override
    public ResultMessageBean updatePlanSeat(int planId, String seatName, int seatNum) {
        planDao.updatePlanSeat(planId, seatName, seatNum);
        return new ResultMessageBean(true);
    }
}