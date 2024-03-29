package tickets.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "planSeats")
public class PlanSeat implements Serializable {

    @Id
    private int id;

    private int planId;

    private String name;

    private int number;

    private double price;

    private String seats;

    public PlanSeat() {
    }

    public PlanSeat(int planId, String name, int number, double price, String seats) {
        this.planId = planId;
        this.name = name;
        this.number = number;
        this.price = price;
        this.seats = seats;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
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

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }
}
