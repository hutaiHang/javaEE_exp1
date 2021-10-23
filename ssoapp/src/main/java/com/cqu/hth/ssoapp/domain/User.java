/*
 * @Description: 用户信息
 * @Autor: hutaihang
 * @Date: 2021-10-22 18:08:26
 * @Coding: UTF-8
 */
package com.cqu.hth.ssoapp.domain;

import lombok.Data;

@Data
public class User {
    private String userName;
    private String pwd;

    public String getUserName()
    {
        return this.userName;
    }

    public void setUserName(String newName)
    {
        this.userName=newName;
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
