package tickets.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tickets.bean.CouponBean;
import tickets.bean.ResultMessageBean;
import tickets.bean.UserBean;
import tickets.dao.UserDao;
import tickets.model.Coupon;
import tickets.model.User;
import tickets.service.UserService;
import tickets.util.CouponUtil;
import tickets.util.MailUtil;
import tickets.util.MemberLevelUtil;
import tickets.util.RandomCodeUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static RegisterUserMap registerUserMap = new RegisterUserMap(); // 存储未验证用户
    private MemberLevelUtil memberLevelUtil = new MemberLevelUtil();
    private CouponUtil couponUtil = new CouponUtil();

    @Autowired
    UserDao userDao;

    @Override
    public ResultMessageBean login(String email, String password) {
        User user = userDao.getUser(email);
        if (user.getIsMember() == 1 && password.equals(user.getPassword())) {
            return new ResultMessageBean(true, email);
        }
        return new ResultMessageBean(false, "fail");
    }

    @Override
    public ResultMessageBean register(String email, String password) {
        User user = userDao.getUser(email);
        if (user == null) {
            User newUser = new User(email, email, password, 1, 1, 0, password, 10000);

            String randomCode = RandomCodeUtil.generateUniqueCode();
            registerUserMap.put(randomCode, newUser);
            registerUserMap.setTime(randomCode);
            new Thread(new MailUtil(email, randomCode)).start();
            return new ResultMessageBean(true);
        }
        return new ResultMessageBean(false, "fail");
    }

    @Override
    public ResultMessageBean verify(String randomCode) {
        User user = registerUserMap.get(randomCode);
        if (user != null) {
            userDao.saveOrUpdateUser(user);
            registerUserMap.remove(randomCode);
            return new ResultMessageBean(true);
        }
        return new ResultMessageBean(false, "fail");
    }

    @Override
    public ResultMessageBean cancelMember(String email) {
        User user = userDao.getUser(email);
        if (user != null) {
            user.setIsMember(0);
            userDao.saveOrUpdateUser(user);
            return new ResultMessageBean(true);
        }
        return new ResultMessageBean(false, "fail");
    }

    @Override
    public UserBean getUser(String email) {
        User user = userDao.getUser(email);

        return new UserBean(user.getEmail(), user.getName(), user.getPassword(), user.getIsMember(),
                memberLevelUtil.getLevelName(user.getScore()), user.getCurrentScore(), user.getPin(), user.getAccount());
    }

    @Override
    public ResultMessageBean updateUserInfo(String email, UserBean newUser) {
        User user = userDao.getUser(email);
        user.setName(newUser.getName());
        user.setPassword(newUser.getPassword());
        user.setPin(newUser.getPin());
        userDao.saveOrUpdateUser(user);

        return new ResultMessageBean(true);
    }

    @Override
    public List<CouponBean> getCoupon(String email) {
        List<Coupon> coupons = userDao.getCoupon(email);
        List<CouponBean> res = new ArrayList<>();
        for (Coupon coupon : coupons) {
            res.add(new CouponBean(coupon.getEmail(), couponUtil.getCouponName(coupon.getCoupon()), coupon.getNumber()));
        }
        return res;
    }

    @Override
    public ResultMessageBean convertCoupon(String email, int couponId) {
        User user = userDao.getUser(email);

        if (user.getCurrentScore() < couponUtil.getCouponScore(couponId)) {
            return new ResultMessageBean(false, "会员积分不足，无法兑换");
        }

        userDao.saveOrUpdateCoupon(email, couponId);
        user.setCurrentScore(user.getCurrentScore() - couponUtil.getCouponScore(couponId));
        userDao.saveOrUpdateUser(user);

        return new ResultMessageBean(true);
    }
}
