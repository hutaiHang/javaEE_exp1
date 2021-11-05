/*
 * @Description: 子系统1
 * @Autor: hutaihang
 * @Date: 2021-10-20 18:18:18
 * @Coding: UTF-8
 */
package com.cqu.hth.ssoapp.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.jni.Time;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class EnterSysController {

    @GetMapping(value = "/ssoCenter")
    public String ssoCenter(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("sso get");
        return "sso";
    }

    @GetMapping(value = "/sys1")
    public String sys1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("sys1 get");
        return "sys1";
    }

    @GetMapping(value = "/sys2")
    public String sys2(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("sys2 get");
        return "sys2";
    }
}
