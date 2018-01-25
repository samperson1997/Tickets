package tickets.bean;

public class SeatPriceBean {

    private String seatName;

    private int seatNum;

    private double seatPrice;

    public SeatPriceBean() {
    }

    public SeatPriceBean(String seatName, int seatNum, double seatPrice) {
        this.seatName = seatName;
        this.seatNum = seatNum;
        this.seatPrice = seatPrice;
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

    public double getSeatPrice() {
        return seatPrice;
    }

    public void setSeatPrice(double seatPrice) {
        this.seatPrice = seatPrice;
    }
}
