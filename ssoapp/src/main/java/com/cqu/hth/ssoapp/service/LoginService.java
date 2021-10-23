/*
 * @Description: 检查用户信息是否匹配
 * @Autor: hutaihang
 * @Date: 2021-10-20 23:54:09
 * @Coding: UTF-8
 */
package com.cqu.hth.ssoapp.service;


import org.springframework.beans.factory.annotation.Autowired;

public class LoginService {
    @Autowired
    IRedisService iRedisService;

    public boolean checkInfo(String userName,String pwd)
    {
        return pwd == iRedisService.get(userName).toString();
    }
}
