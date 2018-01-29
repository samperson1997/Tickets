package tickets.dao;

import tickets.model.Seat;
import tickets.model.Venue;

import java.util.List;

public interface VenueDao {

    /**
     * 根据识别码获得场馆
     *
     * @param id
     * @return
     */
    Venue getVenueById(String id);

    /**
     * 根据名称获得场馆
     *
     * @param name
     * @return
     */
    Venue getVenueByName(String name);

    /**
     * 新建场馆或更新场馆信息
     *
     * @param venue
     */
    boolean saveOrUpdateVenue(Venue venue);

    /**
     * 获得目前数据库中场馆数
     *
     * @return
     */
    int getVenueNum();

    /**
     * 新建座位或更新座位信息
     *
     * @param id
     * @param seatList
     */
    boolean saveOrUpdateSeat(String id, List<Seat> seatList);


    /**
     * 根据场馆识别码获得场馆座位列表
     *
     * @param id
     * @return
     */
    List<Seat> getSeat(String id);

    /**
     * 获得待审批场馆列表
     *
     * @return
     */
    List<Venue> getUncheckedVenues();

    /**
     * 获得所有场馆列表
     *
     * @return
     */
    List<Venue> getVenues();
}
