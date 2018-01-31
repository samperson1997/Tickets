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

    private double realPrice;

    private int isPaid;

    private int isSeatSelected;

    private int isAssigned;

    private int isUsed;

    private int isClosed;

    private int isOut;

    private String seatName;

    private int seatNum;

    private String seatAssigned;


    public Order() {
    }

    public Order(int orderId, String email, int planId, double price, double realPrice, int isPaid, int isSeatSelected, int isAssigned, int isUsed, int isClosed, int isOut, String seatName, int seatNum, String seatAssigned) {
        this.orderId = orderId;
        this.email = email;
        this.planId = planId;
        this.price = price;
        this.realPrice = realPrice;
        this.isPaid = isPaid;
        this.isSeatSelected = isSeatSelected;
        this.isAssigned = isAssigned;
        this.isUsed = isUsed;
        this.isClosed = isClosed;
        this.isOut = isOut;
        this.seatName = seatName;
        this.seatNum = seatNum;
        this.seatAssigned = seatAssigned;
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

    public double getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(double realPrice) {
        this.realPrice = realPrice;
    }

    public int getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(int isPaid) {
        this.isPaid = isPaid;
    }

    public int getIsSeatSelected() {
        return isSeatSelected;
    }

    public void setIsSeatSelected(int isSeatSelected) {
        this.isSeatSelected = isSeatSelected;
    }

    public int getIsAssigned() {
        return isAssigned;
    }

    public void setIsAssigned(int isAssigned) {
        this.isAssigned = isAssigned;
    }

    public int getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(int isUsed) {
        this.isUsed = isUsed;
    }

    public int getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(int isClosed) {
        this.isClosed = isClosed;
    }

    public int getIsOut() {
        return isOut;
    }

    public void setIsOut(int isOut) {
        this.isOut = isOut;
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
