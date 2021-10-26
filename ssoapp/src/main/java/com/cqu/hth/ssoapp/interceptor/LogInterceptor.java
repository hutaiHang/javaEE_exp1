/*
 * @Description: 子系统1拦截器
 * @Autor: hutaihang
 * @Date: 2021-10-21 21:41:12
 * @Coding: UTF-8
 */
package com.cqu.hth.ssoapp.interceptor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.jni.Time;
import org.springframework.web.servlet.HandlerInterceptor;

import io.netty.handler.codec.serialization.ObjectEncoder;

public class LogInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws IOException,ServletException
    {
        String targetService = request.getRequestURI().substring(1); 
        Object isLogin = request.getSession().getAttribute("islogin");
        if(isLogin!=null && isLogin.equals((Object)true)) //若已登录该子系统，直接访问资源
            return true;

        String ssoCenterUrl = "/ssoServerLogin";
        //跳转到sso认证中心
        if(request.getParameter("token")==null) //没有token，需要登录
        {
            response.sendRedirect(ssoCenterUrl+"?service="+targetService);
            return false;
        }
        
        String token = request.getParameter("token");
        System.out.println(token);

        //检查token是否有效
        request.getRequestDispatcher("/checkToken?token="+token+"&service="+targetService).forward(request, response);
        return false;
    }
}