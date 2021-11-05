/*
 * @Description: 
 * @Autor: hutaihang
 * @Date: 2021-10-20 17:39:39
 * @Coding: UTF-8
 */
package com.cqu.hth.ssoapp.domain;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;

public class Session {
    @Id
    private String token;
    private ArrayList<String> loginWebs = new ArrayList<String>();

    public void setToken(String tok) {
        this.token = tok;
        return;
    }

    public String getToken() {
        return this.token;
    }

    public boolean addWebs(String webString) {
        loginWebs.add(webString);
        return true;
    }

    public String[] getLoginWebs() {
        String[] totalWebs = (String[]) loginWebs.toArray();
        return totalWebs;
    }
}
