package tickets.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tickets.bean.ResultMessageBean;
import tickets.bean.SeatBean;
import tickets.bean.VenueMiniBean;
import tickets.dao.VenueDao;
import tickets.model.Seat;
import tickets.model.Venue;
import tickets.service.ManagerService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    VenueDao venueDao;

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
}
