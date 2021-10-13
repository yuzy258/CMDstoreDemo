package com.example.demostore.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    //检测全局session对象是否有uid数据，有则放行，若没有，则重定向到登录页面
    //handler 处理器（url+controller映射）
    public boolean preHandle(HttpServletRequest request,//响应对象
                             HttpServletResponse response,//返回对象
                             Object handler) throws Exception {
        Object obj = request.getSession().getAttribute("uid");
        if(obj==null){
            response.sendRedirect("/web/login.html");
            return false;
        }
        return true;
    }
}
