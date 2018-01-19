package tickets.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "orders")
public class Order implements Serializable {
    @Id
    private int orderId;

    private String email;

    private int planId;

    private double price;

    private int isSeatSelected;

    private int isClosed;

    private int isUsed;

    public Order() {
    }

    public Order(int orderId, String email, int planId, double price, int isSeatSelected, int isClosed, int isUsed) {
        this.orderId = orderId;
        this.email = email;
        this.planId = planId;
        this.price = price;
        this.isSeatSelected = isSeatSelected;
        this.isClosed = isClosed;
        this.isUsed = isUsed;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
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

    public int getIsSeatSelected() {
        return isSeatSelected;
    }

    public void setIsSeatSelected(int isSeatSelected) {
        this.isSeatSelected = isSeatSelected;
    }

    public int getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(int isClosed) {
        this.isClosed = isClosed;
    }

    public int getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(int isUsed) {
        this.isUsed = isUsed;
    }
}
