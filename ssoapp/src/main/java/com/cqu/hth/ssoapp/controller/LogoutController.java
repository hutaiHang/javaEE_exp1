/*
 * @Description: 单点退出接口
 * @Autor: hutaihang
 * @Date: 2021-10-28 16:13:14
 * @Coding: UTF-8
 */
package com.cqu.hth.ssoapp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public void logOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpSession session = request.getSession();
        System.out.println(session.getId());
        session.removeAttribute("token"); // 清除token，销毁全局会话
        // String[] urlList = { "/sys1", "/sys2", "/ssoCenter" };
        // String sessionId = null;
        // Cookie[] cookies = request.getCookies();
        // for (Cookie cookie : cookies) {
        // if(cookie.getName().equals("SESSION"))
        // sessionId = cookie.getValue();
        // }
        // for (String url : urlList) {
        // //生成一个get请求
        // HttpGet httpget = new HttpGet("http://localhost:8080" + url +
        // "?logout=true");
        // System.out.println(sessionId);
        // httpget.setHeader("Cookie", "SESSION=" + sessionId);
        // CloseableHttpResponse tmpresponse = httpclient.execute(httpget);//向所有系统发送注销请求
        // }
        session.invalidate();
        response.sendRedirect("/ssoServerLogin"); // 引导至登陆界面
    }

}
