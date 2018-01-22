package tickets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessageBean login(HttpServletRequest request, String email, String password) {

        return userService.login(email, password);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessageBean register(String email, String password) {
        return userService.login(email, password);
    }

    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    public void verify(HttpServletResponse response, @RequestParam("code") String code) throws Exception {

        if (userService.verify(code).result) {
            response.sendRedirect("/login.html");
        } else {
            response.sendRedirect("/register.html");
        }
    }

    @RequestMapping(value = "/cancelMember", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessageBean cancelMember(String email) {
        return userService.cancelMember(email);
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public UserBean getUserInfo(String email) {

        return userService.getUser(email);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessageBean updateUserInfo(UserBean userBean) {
        return userService.updateUserInfo(userBean.getEmail(), userBean);
    }

    @RequestMapping(value = "/coupon", method = RequestMethod.GET)
    @ResponseBody
    public List<CouponBean> getCoupon(String email) {
        return userService.getCoupon(email);
    }

    @RequestMapping(value = "/convertCoupon", method = RequestMethod.GET)
    @ResponseBody
    public ResultMessageBean convertCoupon(String email, int couponId) {
        return userService.convertCoupon(email, couponId);
    }
}
