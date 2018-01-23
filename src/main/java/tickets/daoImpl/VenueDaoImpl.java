package tickets.daoImpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tickets.dao.VenueDao;
import tickets.model.Seat;
import tickets.model.Venue;

import java.util.List;

@Repository
public class VenueDaoImpl implements VenueDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Venue getVenueById(String id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        Query<Venue> query = session.createNativeQuery("SELECT * FROM venues WHERE id = ?", Venue.class);
        query.setParameter(1, id);
        Venue venue = query.uniqueResult();

        tx.commit();
        session.close();

        return venue;
    }

    @Override
    public Venue getVenueByName(String name) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query<Venue> query = session.createNativeQuery("SELECT * FROM venues WHERE name = ?", Venue.class);
        query.setParameter(1, name);
        Venue venue = query.uniqueResult();

        tx.commit();
        session.close();

        return venue;
    }

    @Override
    public boolean saveOrUpdateVenue(Venue venue) {

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        session.saveOrUpdate(venue);
        tx.commit();
        session.close();

        return true;
    }

    @Override
    public int getVenueNum() {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Query<Venue> query = session.createNativeQuery("SELECT * FROM venues", Venue.class);
        int venueNum = query.getResultList().size();

        tx.commit();
        session.close();

        return venueNum;
    }

    @Override
    public boolean saveOrUpdateSeat(String id, List<Seat> seatList) {

        System.out.println("dao");

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        Query<Seat> query = session.createNativeQuery("SELECT * FROM seats WHERE venueId = ?", Seat.class);
        query.setParameter(1, id);
        List<Seat> seats = query.getResultList();
        if (seats != null && seats.size() != 0) {
            for (Seat seat : seats) {
                session.delete(seat);
            }
        }

        for (Seat seat : seatList) {
            session.saveOrUpdate(seat);
            tx.commit();
            session = sessionFactory.getCurrentSession();
            tx = session.beginTransaction();
        }

        session.close();
        return true;
    }

    @Override
    public List<Seat> getSeat(String id) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query<Seat> query = session.createNativeQuery("SELECT * FROM seats WHERE venueId = ?", Seat.class);
        query.setParameter(1, id);
        List<Seat> seats = query.getResultList();

        tx.commit();
        session.close();

        return seats;
    }

    @Override
    public List<Venue> getUncheckedVenues() {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Query<Venue> query = session.createNativeQuery("SELECT * FROM venues WHERE isChecked = 0", Venue.class);
        List<Venue> list = query.getResultList();

        tx.commit();
        session.close();

        return list;
    }
}
