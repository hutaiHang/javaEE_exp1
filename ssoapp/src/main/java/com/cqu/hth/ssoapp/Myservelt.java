/*
 * @Description: 测试文件
 * @Autor: hutaihang
 * @Date: 2021-10-20 13:31:18
 * @Coding: UTF-8
 */
package com.cqu.hth.ssoapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(urlPatterns = "/test")
public class Myservelt extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServerException, IOException {
        System.out.println(request.getParameter("token"));
        if (request.getParameter("token") != null) {
            System.out.println("get method!!!");
            String token = (String) request.getParameter("token");
            String trueToken = (String) request.getSession().getAttribute("token");
            if (trueToken != null && trueToken.equals(token)) {
                response.setHeader("validToken", "true");
                return;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServerException, IOException {
        doGet(request, response);
    }
}
