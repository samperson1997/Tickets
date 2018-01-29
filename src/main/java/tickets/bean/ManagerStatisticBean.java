package tickets.bean;

public class ManagerStatisticBean {

    // 会员
    private int userNum;

    private int[] levelUserNum;

    // 场馆
    private int venueNum;

    private int uncheckedVenueNum;

    // 活动
    private int planNum;

    private int endPlanNum;

    private int[] typePlanNum;

    // 订单
    private int allOrders;

    private int closedOrders;

    private int cancelOrders;

    private double totalPrice;

    private double account;

    public ManagerStatisticBean() {
    }

    public ManagerStatisticBean(int userNum, int[] levelUserNum, int venueNum, int uncheckedVenueNum, int planNum, int endPlanNum, int[] typePlanNum, int allOrders, int closedOrders, int cancelOrders, double totalPrice, double account) {
        this.userNum = userNum;
        this.levelUserNum = levelUserNum;
        this.venueNum = venueNum;
        this.uncheckedVenueNum = uncheckedVenueNum;
        this.planNum = planNum;
        this.endPlanNum = endPlanNum;
        this.typePlanNum = typePlanNum;
        this.allOrders = allOrders;
        this.closedOrders = closedOrders;
        this.cancelOrders = cancelOrders;
        this.totalPrice = totalPrice;
        this.account = account;
    }

    public int getUserNum() {
        return userNum;
    }

    public void setUserNum(int userNum) {
        this.userNum = userNum;
    }

    public int[] getLevelUserNum() {
        return levelUserNum;
    }

    public void setLevelUserNum(int[] levelUserNum) {
        this.levelUserNum = levelUserNum;
    }

    public int getVenueNum() {
        return venueNum;
    }

    public void setVenueNum(int venueNum) {
        this.venueNum = venueNum;
    }

    public int getUncheckedVenueNum() {
        return uncheckedVenueNum;
    }

    public void setUncheckedVenueNum(int uncheckedVenueNum) {
        this.uncheckedVenueNum = uncheckedVenueNum;
    }

    public int getPlanNum() {
        return planNum;
    }

    public void setPlanNum(int planNum) {
        this.planNum = planNum;
    }

    public int getEndPlanNum() {
        return endPlanNum;
    }

    public void setEndPlanNum(int endPlanNum) {
        this.endPlanNum = endPlanNum;
    }

    public int[] getTypePlanNum() {
        return typePlanNum;
    }

    public void setTypePlanNum(int[] typePlanNum) {
        this.typePlanNum = typePlanNum;
    }

    public int getAllOrders() {
        return allOrders;
    }

    public void setAllOrders(int allOrders) {
        this.allOrders = allOrders;
    }

    public int getClosedOrders() {
        return closedOrders;
    }

    public void setClosedOrders(int closedOrders) {
        this.closedOrders = closedOrders;
    }

    public int getCancelOrders() {
        return cancelOrders;
    }

    public void setCancelOrders(int cancelOrders) {
        this.cancelOrders = cancelOrders;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getAccount() {
        return account;
    }

    public void setAccount(double account) {
        this.account = account;
    }
}
