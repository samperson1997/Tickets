package tickets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tickets.bean.CouponBean;
import tickets.bean.ResultMessageBean;
import tickets.bean.UserBean;
import tickets.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     *
     * @param request
     * @param email
     * @param password
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessageBean login(HttpServletRequest request, String email, String password) {

        return userService.login(email, password);
    }


    /**
     * 注册
     *
     * @param email
     * @param password
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessageBean register(String email, String password) {
        return userService.login(email, password);
    }

    /**
     * 用户邮箱确认
     *
     * @param response
     * @param code
     * @throws Exception
     */
    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    public void verify(HttpServletResponse response, @RequestParam("code") String code) throws Exception {

        if (userService.verify(code).result) {
            response.sendRedirect("/login.html");
        } else {
            response.sendRedirect("/register.html");
        }
    }

    /**
     * 取消会员
     *
     * @param email
     * @return
     */
    @RequestMapping(value = "/cancelMember", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessageBean cancelMember(String email) {
        return userService.cancelMember(email);
    }


    /**
     * 获得用户信息
     *
     * @param email
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public UserBean getUserInfo(String email) {

        return userService.getUser(email);
    }

    /**
     * 更新用户信息
     *
     * @param userBean
     * @return
     */
    @RequestMapping(
            value = "/edit",
            method = RequestMethod.POST,
            consumes = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public ResultMessageBean updateUserInfo(@RequestBody UserBean userBean) {
        return userService.updateUserInfo(userBean.getEmail(), userBean);
    }

    /**
     * 用户下订单或取消订单后更新用户信息
     *
     * @param email
     * @param account
     * @param increaseScore
     * @return
     */
    @RequestMapping(
            value = "/order",
            method = RequestMethod.POST
    )
    @ResponseBody
    public ResultMessageBean updateUserInfoAfterOrder(String email, double account, int increaseScore) {

        return userService.updateUserInfoAfterOrder(email, account, increaseScore);
    }

    /**
     * 获得用户优惠券列表
     *
     * @param email
     * @return
     */
    @RequestMapping(value = "/coupon", method = RequestMethod.GET)
    @ResponseBody
    public List<CouponBean> getCoupon(String email) {
        return userService.getCoupon(email);
    }

    /**
     * 兑换优惠券
     *
     * @param email
     * @param couponId
     * @return
     */
    @RequestMapping(value = "/convertCoupon", method = RequestMethod.GET)
    @ResponseBody
    public ResultMessageBean convertCoupon(String email, int couponId) {
        return userService.convertCoupon(email, couponId);
    }

    /**
     * 使用优惠券
     *
     * @param email
     * @param couponId
     * @return
     */
    @RequestMapping(value = "/useCoupon", method = RequestMethod.GET)
    @ResponseBody
    public ResultMessageBean useCoupon(String email, int couponId) {
        return userService.useCoupon(email, couponId);
    }

    /**
     * 获得用户折扣
     *
     * @param email
     * @return
     */
    @RequestMapping(value = "/discount", method = RequestMethod.GET)
    @ResponseBody
    public double getDiscount(String email) {
        return userService.getDiscount(email);
    }
}
