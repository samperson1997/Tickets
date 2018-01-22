package tickets.service;

import tickets.bean.ResultMessageBean;
import tickets.bean.SeatBean;
import tickets.bean.VenueBean;

import java.util.List;

public interface VenueService {

    /**
     * 登录
     *
     * @param id       场馆识别码
     * @param password 场馆密码
     * @return 当前登录状态集
     */
    ResultMessageBean login(String id, String password);

    /**
     * 注册
     *
     * @param name     场馆名
     * @param password 场馆密码
     * @return 注册状态
     */
    ResultMessageBean register(String name, String password, String location);

    /**
     * 获得场馆信息
     *
     * @param id
     * @return
     */
    VenueBean getVenue(String id);

    /**
     * 修改场馆信息
     *
     * @param id
     * @param newVenue
     * @return
     */
    ResultMessageBean updateVenueInfo(String id, VenueBean newVenue);

    /**
     * 更新座位信息
     *
     * @param name
     * @param seatList
     * @return
     */
    ResultMessageBean updateSeatInfo(String name, List<SeatBean> seatList);

    /**
     * 获得座位信息
     *
     * @param id
     * @return
     */
    List<SeatBean> getSeats(String id);
}
