package tickets.daoImpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tickets.dao.PlanDao;
import tickets.model.Plan;
import tickets.model.PlanSeat;

import java.util.List;

@Repository
public class PlanDaoImpl implements PlanDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public boolean addPlan(Plan plan) {

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        session.save(plan);

        tx.commit();
        session.close();

        return false;
    }

    @Override
    public boolean addPlanSeat(PlanSeat planSeat) {

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        session.save(planSeat);

        tx.commit();
        session.close();
        return false;
    }

    @Override
    public boolean updatePlanSeat(int planId, String seatName, int seatNum) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        Query<PlanSeat> query = session.createNativeQuery("SELECT * FROM planSeats WHERE planId = ? AND name = ?", PlanSeat.class);
        query.setParameter(1, planId);
        query.setParameter(2, seatName);
        PlanSeat planSeat = query.getSingleResult();
        planSeat.setNumber(planSeat.getNumber() + seatNum);

        session.update(planSeat);

        tx.commit();
        session.close();
        return false;
    }

    @Override
    public List<Plan> getPlansByType(int type) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        Query<Plan> query = session.createNativeQuery("SELECT * FROM plans WHERE type = ?", Plan.class);
        query.setParameter(1, type);
        List<Plan> res = query.getResultList();

        tx.commit();
        session.close();

        return res;
    }

    @Override
    public Plan getPlanByPlanId(int planId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        Query<Plan> query = session.createNativeQuery("SELECT * FROM plans WHERE id = ?", Plan.class);
        query.setParameter(1, planId);
        Plan plan = query.uniqueResult();

        tx.commit();
        session.close();

        return plan;
    }

    @Override
    public List<PlanSeat> getPlanSeat(int planId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        Query<PlanSeat> query = session.createNativeQuery("SELECT * FROM planSeats WHERE planId = ?", PlanSeat.class);
        query.setParameter(1, planId);
        List<PlanSeat> res = query.getResultList();

        tx.commit();
        session.close();

        return res;
    }

    @Override
    public int getPlanNum() {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Query<Plan> query = session.createNativeQuery("SELECT * FROM plans", Plan.class);
        int planNum = query.getResultList().size();

        tx.commit();
        session.close();

        return planNum;
    }
}
