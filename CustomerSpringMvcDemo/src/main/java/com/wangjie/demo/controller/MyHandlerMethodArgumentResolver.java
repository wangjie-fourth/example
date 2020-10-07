package com.wangjie.demo.controller;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import java.util.*;

public class MyHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver, ApplicationContextAware {
    // 复用之前的代码逻辑
    private RequestResponseBodyMethodProcessor delegate;

    // 为了延迟加载该Bean，为了解决空指针问题
    private ApplicationContext applicationContext;


    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(MyRequestBody.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        // note:可能有线程安全问题
        if (delegate == null) {
            this.delegate = (RequestResponseBodyMethodProcessor) this.applicationContext.getBean(RequestMappingHandlerAdapter.class)
                    .getArgumentResolvers()
                    .stream()
                    .filter(p -> p instanceof RequestResponseBodyMethodProcessor)
                    .findFirst()
                    .get();
        }

        Object resolvedArgument = delegate.resolveArgument(methodParameter, modelAndViewContainer, nativeWebRequest, webDataBinderFactory);
        if (resolvedArgument instanceof Map) {
            ((Map)resolvedArgument).put("timestamp", System.currentTimeMillis());
        }

        return resolvedArgument;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
