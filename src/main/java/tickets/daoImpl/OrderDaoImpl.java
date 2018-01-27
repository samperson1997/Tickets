package tickets.daoImpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tickets.dao.OrderDao;
import tickets.model.Order;

import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public boolean saveOrUpdateOrder(Order order) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        session.saveOrUpdate(order);

        tx.commit();
        session.close();

        return true;
    }

    @Override
    public Order getOrderByOrderId(int orderId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        Query<Order> query = session.createNativeQuery("SELECT * FROM orders WHERE orderId = ?", Order.class);
        query.setParameter(1, orderId);
        Order order = query.uniqueResult();

        tx.commit();
        session.close();

        return order;
    }

    @Override
    public int getOrderNum() {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Query<Order> query = session.createNativeQuery("SELECT * FROM orders", Order.class);
        int orderNum = query.getResultList().size();

        tx.commit();
        session.close();

        return orderNum;
    }

    @Override
    public List<Order> getOrderByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Query<Order> query = session.createNativeQuery("SELECT * FROM orders WHERE email = ?", Order.class);
        query.setParameter(1, email);
        List<Order> orderList = query.getResultList();

        tx.commit();
        session.close();

        return orderList;
    }
}
