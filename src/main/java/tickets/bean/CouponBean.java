package tickets.bean;

public class CouponBean {

    private String email;

    private String coupon;

    private int number;

    public CouponBean() {
    }

    public CouponBean(String email, String coupon, int number) {
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

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
