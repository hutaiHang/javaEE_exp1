/*
 * @Description: 用户信息
 * @Autor: hutaihang
 * @Date: 2021-10-22 18:08:26
 * @Coding: UTF-8
 */
package com.cqu.hth.ssoapp.domain;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class User {
    @Id
    private String email;
    private String pwd;
    public String getEmail()
    {
        return this.email;
    }

    public void setEmail(String newEmail)
    {
        this.email = newEmail;
    }

    public String getPwd()
    {
        return this.pwd;
    }

    public void setPwd(String newPwd)
    {
        this.pwd = newPwd;
    }

}
