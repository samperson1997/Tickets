package tickets.bean;

import java.util.List;

public class VenueMiniBean {

    private String id;

    private String name;

    private String location;

    private List<SeatBean> seatList;

    public VenueMiniBean() {
    }

    public VenueMiniBean(String id, String name, String location, List<SeatBean> seatList) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.seatList = seatList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<SeatBean> getSeatList() {
        return seatList;
    }

    public void setSeatList(List<SeatBean> seatList) {
        this.seatList = seatList;
    }
}
