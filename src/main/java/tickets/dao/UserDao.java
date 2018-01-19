package tickets.dao;

import tickets.model.User;

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
    void saveOrUpdateUser(User user);


}
