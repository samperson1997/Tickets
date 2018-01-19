package tickets.service;

import tickets.bean.CouponBean;
import tickets.bean.ResultMessageBean;
import tickets.bean.UserBean;

import java.util.List;

public interface UserService {

    /**
     * 登录
     *
     * @param email    用户账号
     * @param password 用户密码
     * @return 当前登录状态集
     */
    ResultMessageBean login(String email, String password);

    /**
     * 注册
     *
     * @param email    用户账号
     * @param password 用户密码
     * @return 注册状态
     */
    ResultMessageBean register(String email, String password);

    /**
     * 用户邮箱验证
     *
     * @param randomCode 验证码
     * @return 验证是否成功
     */
    ResultMessageBean verify(String randomCode);

    /**
     * 注销会员
     *
     * @param email
     * @return
     */
    ResultMessageBean cancelMember(String email);

    /**
     * 获得会员信息
     *
     * @param email
     * @return
     */
    UserBean getUser(String email);

    /**
     * 修改会员信息
     *
     * @param email
     * @param newUser
     * @return
     */
    ResultMessageBean updateUserInfo(String email, UserBean newUser);

    /**
     * 获得优惠券信息
     *
     * @param email
     * @return
     */
    List<CouponBean> getCoupon(String email);

    /**
     * 兑换优惠券
     *
     * @param email
     * @param couponId
     * @return
     */
    ResultMessageBean convertCoupon(String email, int couponId);
}
