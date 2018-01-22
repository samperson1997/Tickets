package tickets.bean;

import java.io.Serializable;
import java.util.List;

public class VenueSeatBean implements Serializable {

    private String name;

    private List<SeatBean> seatList;

    public VenueSeatBean() {
    }

    public VenueSeatBean(String name, List<SeatBean> seatList) {
        this.name = name;
        this.seatList = seatList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SeatBean> getSeatList() {
        return seatList;
    }

    public void setSeatList(List<SeatBean> seatList) {
        this.seatList = seatList;
    }
}
