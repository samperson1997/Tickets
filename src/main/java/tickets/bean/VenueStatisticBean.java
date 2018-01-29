package tickets.bean;

public class VenueStatisticBean {

    /**
     * 总计划数
     */
    private int planNum;

    /**
     * 已结束计划数
     */
    private int endPlanNum;

    /**
     * 全部订单数
     */
    private int allOrders;

    /**
     * 退订订单数
     */
    private int cancelOrders;

    /**
     * 预定总价
     */
    private double totalPrice;

    /**
     * 财务
     */
    private double account;

    public VenueStatisticBean() {
    }

    public VenueStatisticBean(int planNum, int endPlanNum, int allOrders, int cancelOrders, double totalPrice, double account) {
        this.planNum = planNum;
        this.endPlanNum = endPlanNum;
        this.allOrders = allOrders;
        this.cancelOrders = cancelOrders;
        this.totalPrice = totalPrice;
        this.account = account;
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

    public int getAllOrders() {
        return allOrders;
    }

    public void setAllOrders(int allOrders) {
        this.allOrders = allOrders;
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
