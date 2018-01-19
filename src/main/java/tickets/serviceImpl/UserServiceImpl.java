package tickets.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tickets.dao.UserDao;
import tickets.model.User;
import tickets.service.UserService;
import tickets.util.MailUtil;
import tickets.util.RandomCodeUtil;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private static RegisterUserMap registerUserMap = new RegisterUserMap();//存储未验证用户

    @Autowired
    UserDao userDao;

    @Override
    public Map<String, Object> login(String email, String password) {
        User user = userDao.getUser(email);
        Map<String, Object> map = new HashMap<>();
        if (password.equals(user.getPassword())) {
            map.put("msg", "success");
            map.put("email", user.getEmail());
        } else {
            map.put("msg", "fail");
        }
        return map;
    }

    @Override
    public boolean register(String email, String password) {
        User user = userDao.getUser(email);
        if (user == null) {
            User newUser = new User(email, email, password, 1, 1, 0, password, 10000);

            String randomCode = RandomCodeUtil.generateUniqueCode();
            registerUserMap.put(randomCode, newUser);
            registerUserMap.setTime(randomCode);
            new Thread(new MailUtil(email, randomCode)).start();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean verify(String randomCode) {
        User user = registerUserMap.get(randomCode);
        if (user != null) {
            userDao.saveOrUpdateUser(user);
            registerUserMap.remove(randomCode);
            return true;
        } else {
            return false;
        }
    }
}
