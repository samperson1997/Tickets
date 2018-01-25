package tickets.bean;

import java.util.List;

public class PlanSeatBean {

    private String venueId;

    private String startTime;

    private String endTime;

    private int type;

    private String introduction;

    private List<SeatPriceBean> seatPriceBeanList;

    public PlanSeatBean() {
    }

    public PlanSeatBean(String venueId, String startTime, String endTime, int type, String introduction, List<SeatPriceBean> seatPriceBeanList) {
        this.venueId = venueId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
        this.introduction = introduction;
        this.seatPriceBeanList = seatPriceBeanList;
    }

    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public List<SeatPriceBean> getSeatPriceBeanList() {
        return seatPriceBeanList;
    }

    public void setSeatPriceBeanList(List<SeatPriceBean> seatPriceBeanList) {
        this.seatPriceBeanList = seatPriceBeanList;
    }
}
