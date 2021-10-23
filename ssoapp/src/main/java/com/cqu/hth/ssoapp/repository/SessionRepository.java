/*
 * @Description: mongodb存储session
 * @Autor: hutaihang
 * @Date: 2021-10-20 17:37:01
 * @Coding: UTF-8
 */
package com.cqu.hth.ssoapp.repository;

import org.springframework.stereotype.Repository;

import com.cqu.hth.ssoapp.domain.Session;

import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface SessionRepository extends MongoRepository<Session,String> {
    
}
