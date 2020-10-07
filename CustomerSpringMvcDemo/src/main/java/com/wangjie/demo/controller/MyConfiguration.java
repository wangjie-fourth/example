package com.wangjie.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.List;

@Configuration
public class MyConfiguration implements WebMvcConfigurer {

    @Autowired
    private RequestMappingHandlerAdapter adapter;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(myHandlerMethodArgumentResolver());
    }

    @Bean
    public MyHandlerMethodArgumentResolver myHandlerMethodArgumentResolver() {
        return new MyHandlerMethodArgumentResolver();
    }
}
