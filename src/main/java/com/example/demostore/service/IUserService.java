package com.example.demostore.service;
import com.example.demostore.entity.User;

public interface IUserService {
    //用户注册
    void reg(User user);
    User login(String username,String password);
    void changePassword(Integer uid,String username,
                        String oldPassword,String newPassword);
}

