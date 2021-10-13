package com.example.demostore.controller;

import com.example.demostore.service.ex.*;
import com.example.demostore.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

public class BaseController {
    public static final int OK = 200;
    // @ExceptionHandler用于统一处理方法抛出的异常

    @ExceptionHandler(ServiceException.class)
    public JsonResult<Void> handleException(Throwable e) {
        JsonResult<Void> result = new JsonResult<Void>(e);
        if (e instanceof UsernameDuplicateException) {
            result.setState(4000);
        }
        else if (e instanceof UserNotFoundException) {
            result.setState(5001);
            result.setMessage("用户数据不存在异常");
        }
        else if (e instanceof PasswordNotMatchException) {
            result.setState(5002);
            result.setMessage("用户名密码错误的异常");
        }
        else if (e instanceof InsertException) {
            result.setState(5000);
        }
        else if (e instanceof UpdateException) {
            result.setState(5003);
            result.setMessage("更新数据时异常");
        }
        return result;
    }
    protected final Integer getidFromSession(HttpSession session){
        return Integer.valueOf( session.getAttribute("uid")
                .toString());
    }
    protected final String getnameFromSession(HttpSession session)
    {
       return  session.getAttribute("username").toString();
    }
}
