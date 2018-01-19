package tickets.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "orderSeats")
public class OrderSeat implements Serializable {

    @Id
    private int id;

    private int orderId;

    private String name;

    private int number;

    private double price;

    public OrderSeat() {
    }

    public OrderSeat(int orderId, String name, int number, double price) {
        this.orderId = orderId;
        this.name = name;
        this.number = number;
        this.price = price;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
