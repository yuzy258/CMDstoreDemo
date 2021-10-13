package com.example.demostore.mapper;

import com.example.demostore.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTests {
    @Autowired
private UserMapper userMapper;
    @Test
    public void insert() {
        User user = new User();
        user.setUsername("tim");
        user.setPassword("456123");
        Integer rows = userMapper.insert(user);
        System.out.println("rows=" + rows);
    }

    @Test
    public void findByUsername() {
        String username = "tim";
        User result = userMapper.findByName(username);
        System.out.println(result);
    }
    @Test
    public void updatePasswordByUid(){
        userMapper.updatePasswordByUid(2,"76122"
                ,"管理员",new Date());

    }
    @Test
    public void  findByUid(){
        System.out.println(userMapper.findByUid(7));
    }
    @Test
    public void updateInfoByid(){
        User user=new User(10,"2test003@1.com",1);
       /* user.setUid(10);
        user.setEmail("test003@qq.com");
        user.setGender(1);*/
        userMapper.updateInfoByid(user);
    }
}
