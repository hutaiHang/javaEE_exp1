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
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.cqu.hth.ssoapp.domain.User;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.tags.ParamAware;
import org.apache.tomcat.jni.Time;

@Controller
@RequestMapping("/")
public class LoginController {

    public String qSys;
    @GetMapping("/ssoServerLogin")
    public String login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        qSys = "/"+request.getParameter("service");
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("token"))
                response.sendRedirect(qSys+"?token="+cookie.getValue());
        }
        return "login";
    }

    @PostMapping("/ssoServerLogin")
    public void login(@Valid User user, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String userName = user.getUserName();
        String password = user.getPwd();
        System.out.println(userName + ":" + password);
        String token = UUID.randomUUID().toString();
        HttpSession session = request.getSession();
        session.setAttribute("token", token);
        
        Cookie newCookie = new Cookie("token", token);
        response.addCookie(newCookie); //加token添加进cookie，保存在浏览器
        response.sendRedirect(qSys + "?token=" + token);
    }

    @GetMapping(value = "/checkToken")
    protected void checkToken(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String token = request.getParameter("token");
        String qPath = "/" + request.getParameter("service");
        System.out.println(token);
        if (request.getSession().getAttribute("token") != null)
        {
            String trueToken = (String) request.getSession().getAttribute("token");
            if (token.equals(trueToken)) {
                request.getSession().setAttribute("islogin", true);
                System.out.println("okkk");
                request.getRequestDispatcher(qPath).forward(request, response);
                return;
            }
        }
        request.getRequestDispatcher(qPath).forward(request, response);
        return;
    }

    
}