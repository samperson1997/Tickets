package tickets.bean;

public class UserStatisticBean {

    private int allOrders;

    private int closedOrders;

    private int cancelOrders;

    private double totalPrice;

    private int type1Order;

    private int type2Order;

    private int type3Order;

    private int type4Order;

    private int type5Order;

    public UserStatisticBean() {
    }

    public UserStatisticBean(int allOrders, int closedOrders, int cancelOrders, double totalPrice, int type1Order, int type2Order, int type3Order, int type4Order, int type5Order) {
        this.allOrders = allOrders;
        this.closedOrders = closedOrders;
        this.cancelOrders = cancelOrders;
        this.totalPrice = totalPrice;
        this.type1Order = type1Order;
        this.type2Order = type2Order;
        this.type3Order = type3Order;
        this.type4Order = type4Order;
        this.type5Order = type5Order;
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

    public int getType1Order() {
        return type1Order;
    }

    public void setType1Order(int type1Order) {
        this.type1Order = type1Order;
    }

    public int getType2Order() {
        return type2Order;
    }

    public void setType2Order(int type2Order) {
        this.type2Order = type2Order;
    }

    public int getType3Order() {
        return type3Order;
    }

    public void setType3Order(int type3Order) {
        this.type3Order = type3Order;
    }

    public int getType4Order() {
        return type4Order;
    }

    public void setType4Order(int type4Order) {
        this.type4Order = type4Order;
    }

    public int getType5Order() {
        return type5Order;
    }

    public void setType5Order(int type5Order) {
        this.type5Order = type5Order;
    }
}
