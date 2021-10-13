package com.example.demostore.controller;

import com.example.demostore.entity.User;
import com.example.demostore.service.IUserService;
import com.example.demostore.service.ex.InsertException;
import com.example.demostore.service.ex.UsernameDuplicateException;
import com.example.demostore.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("users")
public class UserController extends BaseController {
    @Autowired
    private IUserService iUserService;
    @RequestMapping("reg")
    public JsonResult<Void> reg(User user)
    {
        iUserService.reg(user);
        return new JsonResult<>(OK) ;
    }
    @RequestMapping("login")
    public JsonResult<User> login(String username, String password,
                                  HttpSession session){
        User data=iUserService.login(username,password);
        session.setAttribute("uid",data.getUid());
        session.setAttribute("username",data.getUsername());
        //test session
        System.out.println(getidFromSession(session));
        System.out.println(getnameFromSession(session));
        return new JsonResult<User>(OK,data);
    }
    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(String oldPassword,
                                           String newPassword,
                                           HttpSession session){
        Integer uid = getidFromSession(session);
        String username = getnameFromSession(session);
        iUserService.changePassword(uid,username,oldPassword,newPassword);
        return new JsonResult<>(OK);
    }
   /* @RequestMapping("reg")
    //@RequestBody  //以json格式给到前端
    public JsonResult<Void> reg(User user)
    {
        JsonResult<Void> result=new JsonResult<>();
        try {
            iUserService.reg(user);
            result.setState(200);
            result.setMessage("用户注册成功");
        } catch (UsernameDuplicateException e) {
            result.setState(4000);
            result.setMessage("用户名被占用");
        }catch (InsertException e){
            result.setState(5000);
            result.setMessage("注册时产生未知的异常");
        }
        return  result;
    }*/
}
