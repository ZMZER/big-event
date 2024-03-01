package org.example.service;

import org.example.pojo.User;

public interface UserService {
    //根据用户名查询
    User findUserByUsername(String username);

    //注册
    void register(String username, String password);

    //更新用户信息
    void updateUser(User user);

    //更新用户头像
    void updateAvatar(String avatarUrl);

    //更新用户密码
    void updatePwd(String newPwd);
}
