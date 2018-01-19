package tickets.service;

import java.util.Map;

public interface UserService {

    /**
     * 登录
     *
     * @param email    用户账号
     * @param password 用户密码
     * @return 当前登录状态集
     */
    Map<String, Object> login(String email, String password);

    /**
     * 注册
     *
     * @param email    用户账号
     * @param password 用户密码
     * @return 注册状态
     */
    boolean register(String email, String password);

    /**
     * 用户邮箱验证
     *
     * @param randomCode 验证码
     * @return 验证是否成功
     */
     boolean verify(String randomCode);
}
