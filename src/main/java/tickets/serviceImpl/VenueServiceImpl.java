package tickets.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tickets.bean.ResultMessageBean;
import tickets.bean.SeatBean;
import tickets.bean.VenueBean;
import tickets.dao.VenueDao;
import tickets.model.Seat;
import tickets.model.Venue;
import tickets.service.VenueService;

import java.util.ArrayList;
import java.util.List;

@Service
public class VenueServiceImpl implements VenueService {

    @Autowired
    VenueDao venueDao;

    @Override
    public ResultMessageBean login(String id, String password) {
        Venue venue = venueDao.getVenueById(id);
        if (venue != null) {
            if (venue.getIsChecked() == 0) {
                return new ResultMessageBean(false, "信息审批中, 请稍后尝试");
            } else if (venue.getIsChecked() == -1) {
                return new ResultMessageBean(false, "信息审批未通过, 请联系系统管理员");
            } else if (password.equals(venue.getPassword())) {
                return new ResultMessageBean(true, id);
            }
        }
        return new ResultMessageBean(false, "识别码或密码错误, 请重新尝试");
    }

    @Override
    public ResultMessageBean register(String name, String password, String location) {
        Venue venue = venueDao.getVenueByName(name);
        if (venue == null) {
            Venue newVenue = new Venue(getVenueId(), name, location, 0, password, 0);
            venueDao.saveOrUpdateVenue(newVenue);
            return new ResultMessageBean(true, newVenue.getId());
        }
        return new ResultMessageBean(false, "场馆名称已存在");
    }

    @Override
    public VenueBean getVenue(String id) {
        Venue venue = venueDao.getVenueById(id);
        return new VenueBean(venue.getId(), venue.getName(), venue.getLocation(), venue.getAccount(), venue.getPassword());
    }

    @Override
    public ResultMessageBean updateVenueInfo(String id, VenueBean newVenue) {
        // 查看名字是否有重复
        Venue oldVenue = venueDao.getVenueById(id);
        if (!newVenue.getName().equals(oldVenue.getName())) {
            Venue venue = venueDao.getVenueByName(newVenue.getName());
            if (venue != null) {
                return new ResultMessageBean(false, "场馆名称已存在");
            }
        }

        // 修改信息
        if (!newVenue.getPassword().equals(oldVenue.getPassword())) {
            // 只修改密码，不需要isChecked置为0
            oldVenue.setPassword(newVenue.getPassword());
        } else {
            // 只修改其他信息，需要isChecked置为0
            oldVenue.setName(newVenue.getName());
            oldVenue.setLocation(newVenue.getLocation());
            oldVenue.setIsChecked(0);
        }
        venueDao.saveOrUpdateVenue(oldVenue);

        return new ResultMessageBean(true);
    }

    @Override
    public ResultMessageBean updateSeatInfo(String name, List<SeatBean> seatList) {

        List<Seat> list = new ArrayList<>();
        for (SeatBean seatBean : seatList) {
            Seat seat = new Seat(venueDao.getVenueByName(name).getId(), seatBean.getSeatName(), seatBean.getSeatNum());
            list.add(seat);
        }

        venueDao.saveOrUpdateSeat(venueDao.getVenueByName(name).getId(), list);
        return new ResultMessageBean(true);
    }

    @Override
    public List<SeatBean> getSeats(String id) {
        List<Seat> seats = venueDao.getSeat(id);
        List<SeatBean> res = new ArrayList<>();
        for (Seat seat : seats) {
            res.add(new SeatBean(seat.getName(), seat.getNum()));
        }
        return res;
    }

    /**
     * 获得新注册场馆的识别码
     *
     * @return
     */
    private String getVenueId() {
        int venueNum = venueDao.getVenueNum() + 1;
        StringBuilder venueId = new StringBuilder(String.valueOf(venueNum));
        for (int i = venueId.length(); i < 7; i++) {
            venueId.insert(0, "0");
        }
        return venueId.toString();
    }
}
