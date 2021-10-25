/*
 * @Description: 拦截器配置
 * @Autor: hutaihang
 * @Date: 2021-10-22 00:29:59
 * @Coding: UTF-8
 */
package com.cqu.hth.ssoapp.config;

import com.cqu.hth.ssoapp.interceptor.SsoLogInterceptor;
import com.cqu.hth.ssoapp.interceptor.Sys1LogInterceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(new Sys1LogInterceptor()).addPathPatterns("/sys1");
        registry.addInterceptor(new SsoLogInterceptor()).addPathPatterns("/ssoCenter");
    }
}
