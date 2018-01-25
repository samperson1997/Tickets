package tickets.bean;

public class PlanVenueBean {

    private int planId;

    private String venueId;

    private String name;

    private String location;

    private String startTime;

    private String endTime;

    private int type;

    private String introduction;

    private double lowPrice;

    public PlanVenueBean() {
    }

    public PlanVenueBean(int planId, String venueId, String name, String location, String startTime, String endTime, int type, String introduction, double lowPrice) {
        this.planId = planId;
        this.venueId = venueId;
        this.name = name;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
        this.introduction = introduction;
        this.lowPrice = lowPrice;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
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

    public double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(double lowPrice) {
        this.lowPrice = lowPrice;
    }
}
