package tickets.bean;

public class CouponBean {

    private String email;

    private int coupon;

    private int number;

    public CouponBean() {
    }

    public CouponBean(String email, int coupon, int number) {
        this.email = email;
        this.coupon = coupon;
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCoupon() {
        return coupon;
    }

    public void setCoupon(int coupon) {
        this.coupon = coupon;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
