package tickets.daoImpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tickets.dao.UserDao;
import tickets.model.User;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public User getUser(String email) {

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query<User> query = session.createNativeQuery("SELECT * FROM users WHERE email = ?", User.class);
        query.setParameter(1, email);
        User user = query.uniqueResult();

        tx.commit();
        session.close();

        return user;
    }

    @Override
    public void saveOrUpdateUser(User user) {

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        session.saveOrUpdate(user);

        tx.commit();
        session.close();

    }

}
