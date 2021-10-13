package com.example.demostore.service.impl;

import com.example.demostore.entity.User;
import com.example.demostore.mapper.UserMapper;
import com.example.demostore.service.IUserService;
import com.example.demostore.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public void reg(User user) {
        String username = user.getUsername();
        User result = userMapper.findByName(username);
        if(result!=null) {
            //抛出异常
            throw new UsernameDuplicateException("用户名被占用");
        }
        ///
        String oldPassword=user.getPassword();
        String salt= UUID.randomUUID().toString().toUpperCase();
        user.setSalt(salt);
        String md5Password = getMD5Password(oldPassword, salt);
        //md5后的密码设置到user中
        user.setPassword(md5Password);

        //补全数据 isdeleteg
        user.setIsDelete(0);
         //补全数据 日志时间
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date=new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);
        /*
        // 创建当前时间对象 Date now = new Date();  这是我复制粘贴的
         // 补全数据：加密后的密码
          String salt = UUID.randomUUID().toString().toUpperCase();
          String md5Password = getMd5Password(user.getPassword(), salt);
          user.setPassword(md5Password); // 补全数据：盐值 user.setSalt(salt);
          // 补全数据：isDelete(0) user.setIsDelete(0); // 补全数据：4项日志属性
          user.setCreatedUser(username); user.setCreatedTime(now);
           user.setModifiedUser(username); user.setModifiedTime(now);
         */


        //执行业务逻辑
        Integer rows = userMapper.insert(user);
        if(rows!=1)
            throw new InsertException("用户注册中产生的异常");
    }

    @Override
    public User login(String username, String password) {
        User result = userMapper.findByName(username);
        if(result==null){
            throw new UserNotFoundException("用户数据不存在");
        }
        //检测用户的密码是否匹配
        //1.获取数据库加密的密码
        String oldPassword=result.getPassword();
        //2.和用户传递过来的密码比较
        //2.1先获取盐值
        String salt= result.getSalt();
        //2.2将用户的密码按照MD5进行加密
        String newMd5Password = getMD5Password(password, salt);
      //比较密码
        if(!newMd5Password.equals(oldPassword)) {
            throw new PasswordNotMatchException("用户密码错误");
        }
        //判断是否删除  isdelete是否为1
        if(result.getIsDelete()==1)
        {
            throw new UserNotFoundException("用户数据不存在 已删除");
        }
        //这回貌似真查询了 数据太多 发有用的就行
        User user =new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());
        return user;
    }

    @Override
    public void changePassword(Integer uid, String username,
                               String oldPassword, String newPassword) {
        User result = userMapper.findByUid(uid);
        if(result==null||result.getIsDelete()==1){
            throw new UserNotFoundException();
        }
        String oldmd5 = getMD5Password(oldPassword, result.getSalt());
        if(!result.getPassword().equals(oldmd5)){
            throw new PasswordNotMatchException("密码错误");
        }  //之后把新密码添加进去
        String newMd5= getMD5Password(newPassword,result.getSalt());
        Integer rows = userMapper.updatePasswordByUid(uid,
                newMd5, username, new Date());
        if(rows!=1){ throw new UpdateException("更新时异常");
        }
    }

    private String getMD5Password(String password,String salt)
    {   //md5三次加密
        for(int i=0;i<3;i++)
        {
            password=DigestUtils.md5DigestAsHex((salt+password+salt).getBytes()).toUpperCase();
        }
        return password;
    }
}
