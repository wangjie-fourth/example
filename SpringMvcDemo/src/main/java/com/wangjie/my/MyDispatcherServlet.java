package com.wangjie.my;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MyDispatcherServlet extends HttpServlet {
    private Map<Class, Object> container;

    public MyDispatcherServlet(Map<Class, Object> container) {
        this.container = container;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String uri = req.getRequestURI();
        for (Object bean : container.values()) {
            if (bean.getClass().getAnnotation(MyController.class)!=null) {
                Method[] methods = bean.getClass().getMethods();
                for (Method method : methods) {
                    MyGetMapping annotation = method.getAnnotation(MyGetMapping.class);
                    if (annotation != null && annotation.value().equals(uri)) {
                        // 判断参数
                        Class<?>[] parameterTypes = method.getParameterTypes();
                        Object[] params = new Object[parameterTypes.length];
                        for (int i=0; i<parameterTypes.length; i++ ){
                            Class paramType = parameterTypes[i];
                            if (paramType == HttpServletRequest.class) {
                                params[i] = req;
                            } else if (paramType == HttpServletResponse.class) {
                                params[i] = resp;
                            } else {
                                Annotation[] annoOnParam = method.getParameterAnnotations()[i];
                                for (Annotation ann : annoOnParam) {
                                    if (ann.annotationType() == MyRequestParam.class) {
                                        params[i] = req.getParameter(((MyRequestParam)ann).value());
                                        break;
                                    }
                                }
                            }
                        }

                        // 真正处理方法的逻辑
                        Object result;
                        try {
                            result = method.invoke(bean, params);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }

                        // 渲染逻辑
                        MyResponseBody responseBody = method.getAnnotation(MyResponseBody.class);
                        if (responseBody == null) {
                            resp.setHeader("Content-Type", "application/json");
                            resp.getOutputStream().print(result.toString());
                            resp.getOutputStream().flush();
                        } else {
                            resp.setHeader("Content-Type", "application/json");
                            resp.getOutputStream().print("{\"result\":"+result.toString()+"}");
                            resp.getOutputStream().flush();
                        }

                    }
                }
            }
        }
    }
}
