package tickets.bean;

public class OrderBean {

    private String orderId;

    private String email;

    private int planId;

    private double price;

    private double realPrice;

    private int isSeatSelected;

    private String state;

    private String seatName;

    private int seatNum;

    private String seatAssigned;


    public OrderBean() {
    }

    public OrderBean(String orderId, String email, int planId, double price, double realPrice, int isSeatSelected, String state, String seatName, int seatNum, String seatAssigned) {
        this.orderId = orderId;
        this.email = email;
        this.planId = planId;
        this.price = price;
        this.realPrice = realPrice;
        this.isSeatSelected = isSeatSelected;
        this.state = state;
        this.seatName = seatName;
        this.seatNum = seatNum;
        this.seatAssigned = seatAssigned;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
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
}
