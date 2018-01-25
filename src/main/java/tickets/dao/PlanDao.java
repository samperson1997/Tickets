package tickets.dao;

import tickets.model.Plan;
import tickets.model.PlanSeat;

import java.util.List;

public interface PlanDao {

    boolean addPlan(Plan plan);

    boolean addPlanSeat(PlanSeat planSeat);

    List<Plan> getPlan(int type);

    List<PlanSeat> getPlanSeat(int planId);

    int getPlanNum();
}
