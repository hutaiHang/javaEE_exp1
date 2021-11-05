/*
 * @Description: 认证中心登录接口控制
 * @Autor: hutaihang
 * @Date: 2021-10-22 17:05:45
 * @Coding: UTF-8
 */
package com.cqu.hth.ssoapp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.cqu.hth.ssoapp.domain.User;
import com.cqu.hth.ssoapp.service.LoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.tags.ParamAware;

import ch.qos.logback.core.subst.Token;

import org.apache.tomcat.jni.Time;

@Controller
@RequestMapping("/")
public class LoginController {
    @Autowired
    LoginService loginService;

    @GetMapping("/ssoServerLogin")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /**
         * @description: 若已登录，带token重定向请求相应子系统，否则返回sso登录界面
         * @param {*}
         * @return {*}
         */
        String qSys = "/ssoCenter"; // 默认进入sso认证中心
        String targetService = request.getParameter("service");
        if (targetService != null)
            qSys = "/" + targetService;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) // 带有token说明之前登录过，不需要再次登录拿到token
                {
                    Object trueToken = request.getSession().getAttribute("token");
                    if (trueToken != null && cookie.getValue().equals((String) trueToken))
                        response.sendRedirect(qSys + "?token=" + cookie.getValue()); // 若token未过期则直接跳转
                    else // 否则清除token
                    {
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
                }

            }
        }

        ModelAndView mView = new ModelAndView("login");
        mView.getModel().put("service", qSys);

        return mView;
    }

    @RequestMapping(value = "/ssoServerLogin", method = RequestMethod.POST, params = "action=Login")
    public ModelAndView login(@Valid User user, String service, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        String userEmail = user.getEmail();
        String password = user.getPwd();
        boolean rst = loginService.checkAcc(userEmail, password);
        if (rst) {
            String qSys = service;
            System.out.println(qSys);
            String token = UUID.randomUUID().toString();
            HttpSession session = request.getSession();
            session.setAttribute("token", token); // 建立全局会话

            Cookie newCookie = new Cookie("token", token);
            response.addCookie(newCookie); // 加token添加进cookie，保存在浏览器
            response.sendRedirect(qSys + "?token=" + token);
        }
        ModelAndView mView = new ModelAndView("login");
        mView.getModel().put("errorpwd", true);
        mView.getModel().put("service", service);
        return mView;
    }

    @RequestMapping(value = "/ssoServerLogin", method = RequestMethod.POST, params = "action=Sign")
    public ModelAndView sign(@Valid User user, String service, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        loginService.singNewUser(user);
        ModelAndView mView = new ModelAndView("login");
        mView.getModel().put("newUser", true);
        mView.getModel().put("service", service);
        return mView;
    }

    @GetMapping(value = "/checkToken")
    protected void checkToken(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String token = request.getParameter("token");
        String qPath = "/" + request.getParameter("service");
        System.out.println(token);
        if (request.getSession().getAttribute("token") != null) { // 若全局会话未过期
            String trueToken = (String) request.getSession().getAttribute("token");// 真正存放在session里的token
            if (token.equals(trueToken)) {
                request.getSession().setAttribute("islogin", true);// 建立局部会话
                request.getRequestDispatcher(qPath).forward(request, response);
                return;
            }
        }
        response.sendRedirect(qPath);// token过期则重新登录
        // request.getRequestDispatcher(qPath).forward(request, response);//token过期则重新登录
        return;
    }

}