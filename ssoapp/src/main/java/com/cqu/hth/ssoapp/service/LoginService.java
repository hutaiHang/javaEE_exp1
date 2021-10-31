/*
 * @Description: 检查用户信息是否匹配
 * @Autor: hutaihang
 * @Date: 2021-10-20 23:54:09
 * @Coding: UTF-8
 */
package com.cqu.hth.ssoapp.service;


import java.util.Optional;

import com.cqu.hth.ssoapp.domain.User;
import com.cqu.hth.ssoapp.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    UserRepository userRepository;
    public boolean checkAcc(String userEmail,String pwd)
    {
        Optional<User> users = userRepository.findById(userEmail);
        if (!users.isPresent())
            return false;
        String password = users.get().getPwd();
        return password.equals(pwd);
    }
    
    public boolean singNewUser(User user)
    {
        userRepository.insert(user);
        return true;
    }
}
