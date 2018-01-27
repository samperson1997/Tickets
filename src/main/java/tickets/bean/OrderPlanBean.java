package tickets.bean;

public class OrderPlanBean {

    private String orderId;

    private double price;

    private double realPrice;

    private int isSeatSelected;

    private String state;

    private String seatName;

    private int seatNum;

    private String seatAssigned;

    private String name;

    private String location;

    private String startTime;

    private String endTime;

    private int type;

    private String introduction;

    public OrderPlanBean() {
    }

    public OrderPlanBean(String orderId, double price, double realPrice, int isSeatSelected, String state, String seatName, int seatNum, String seatAssigned, String name, String location, String startTime, String endTime, int type, String introduction) {
        this.orderId = orderId;
        this.price = price;
        this.realPrice = realPrice;
        this.isSeatSelected = isSeatSelected;
        this.state = state;
        this.seatName = seatName;
        this.seatNum = seatNum;
        this.seatAssigned = seatAssigned;
        this.name = name;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
        this.introduction = introduction;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(double realPrice) {
        this.realPrice = realPrice;
    }

    public int getIsSeatSelected() {
        return isSeatSelected;
    }

    public void setIsSeatSelected(int isSeatSelected) {
        this.isSeatSelected = isSeatSelected;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public int getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(int seatNum) {
        this.seatNum = seatNum;
    }

    public String getSeatAssigned() {
        return seatAssigned;
    }

    public void setSeatAssigned(String seatAssigned) {
        this.seatAssigned = seatAssigned;
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
}
