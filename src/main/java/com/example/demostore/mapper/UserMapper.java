package com.example.demostore.mapper;

import com.example.demostore.entity.User;

import java.util.Date;

public interface UserMapper {
    Integer insert(User user);
    User findByName(String username);
    Integer updatePasswordByUid(Integer uid, String password
                                , String modifiedUser,
                               Date modifiedTime);
    User findByUid(Integer uid);
    Integer updateInfoByid(User user);
}
