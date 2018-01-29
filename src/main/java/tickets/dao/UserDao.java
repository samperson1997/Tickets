package tickets.dao;

import tickets.model.Coupon;
import tickets.model.User;

import java.util.List;

public interface UserDao {

    /**
     * 根据邮箱获得用户
     *
     * @param email
     * @return
     */
    User getUser(String email);

    /**
     * 新建用户或更新用户信息
     *
     * @param user
     */
    boolean saveOrUpdateUser(User user);

    /**
     * 获得用户优惠券
     *
     * @param email
     * @return
     */
    List<Coupon> getCoupon(String email);

    /**
     * 兑换优惠券
     *
     * @param email
     * @param couponId
     * @return
     */
    boolean addCoupon(String email, int couponId);

    /**
     * 使用优惠券
     *
     * @param email
     * @param couponId
     * @return
     */
    boolean useCoupon(String email, int couponId);

    /**
     * 获得用户列表
     *
     * @return
     */

    List<User> getUserList();
}
