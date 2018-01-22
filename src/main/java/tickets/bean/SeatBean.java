package tickets.bean;

import java.io.Serializable;

public class SeatBean implements Serializable {

    private String seatName;

    private int seatNum;

    public SeatBean() {

    }

    public SeatBean(String name, int num) {
        this.seatName = name;
        this.seatNum = num;
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
}
