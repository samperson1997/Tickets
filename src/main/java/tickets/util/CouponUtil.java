package tickets.util;

public class CouponUtil {
    private final String[] couponName = {"1元优惠券", "2元优惠券", "5元优惠券", "10元优惠券", "20元优惠券", "50元优惠券", "100元优惠券"};
    private final int[] couponScore = {3000, 5500, 12000, 20000, 32000, 70000, 120000};

    public String getCouponName(int couponId) {
        return couponName[couponId - 1];
    }

    public int getCouponScore(int couponId) {
        return couponScore[couponId - 1];
    }
}
