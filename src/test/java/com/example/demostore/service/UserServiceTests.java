package com.example.demostore.service;

import com.example.demostore.entity.User;
import com.example.demostore.mapper.UserMapper;
import com.example.demostore.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.example.demostore.service.*;
///
import com.example.demostore.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTests {
    @Autowired
    private IUserService userService;
    @Test
    public void reg(){
        try {
            User user=new User();
            user.setUsername("test003");
            user.setPassword("123");
            userService.reg(user);
            System.out.println("maosi OK");
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void login(){
        User tom001 = userService.login("test001", "123");
        System.out.println(tom001);
    }
    @Test
    public void changePassword(){
        userService.changePassword(10,"test003","123",
                "456");
    }
}
////

