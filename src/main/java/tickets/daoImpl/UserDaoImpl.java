package tickets.daoImpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tickets.dao.UserDao;
import tickets.model.Coupon;
import tickets.model.User;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public User getUser(String email) {

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        Query<User> query = session.createNativeQuery("SELECT * FROM users WHERE email = ?", User.class);
        query.setParameter(1, email);
        User user = query.uniqueResult();

        tx.commit();
        session.close();

        return user;
    }

    @Override
    public boolean saveOrUpdateUser(User user) {

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        session.saveOrUpdate(user);

        tx.commit();
        session.close();

        return true;
    }

    @Override
    public List<Coupon> getCoupon(String email) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query<Coupon> query = session.createNativeQuery("SELECT * FROM coupons WHERE email = ?", Coupon.class);
        query.setParameter(1, email);
        List<Coupon> coupons = query.getResultList();

        tx.commit();
        session.close();

        return coupons;
    }

    @Override
    public boolean saveOrUpdateCoupon(String email, int couponId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        List<Coupon> coupons = getCoupon(email);
        boolean isExist = false;
        for (Coupon coupon : coupons) {
            if (coupon.getCoupon() == couponId) {
                coupon.setNumber(coupon.getNumber() + 1);
                session.saveOrUpdate(coupon);
                isExist = true;
                break;
            }
        }

        if (!isExist) {
            session.saveOrUpdate(new Coupon(email, couponId, 1));
        }

        tx.commit();
        session.close();
        return true;
    }

}
