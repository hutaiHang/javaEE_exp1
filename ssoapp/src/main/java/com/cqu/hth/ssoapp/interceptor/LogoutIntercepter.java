/*
 * @Description: 退出拦截器
 * @Autor: hutaihang
 * @Date: 2021-10-28 16:15:18
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

public class LogoutIntercepter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException, ServletException {
        Object isLogin = request.getSession().getAttribute("islogin");
        if (isLogin == null || isLogin.equals((Object) false)) // 若已经退出，引导至登录界面
        {
            response.sendRedirect("/ssoServerLogin");
            return false;
        }

        return true;
    }
}