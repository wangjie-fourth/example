package com.wangjie;

import com.google.common.reflect.ClassPath;
import com.wangjie.my.*;
import org.apache.catalina.LifecycleException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.*;
import java.util.stream.Collectors;

public class MySpringApplication {
    public static void main(String[] args) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, LifecycleException {
        // 实现Spring容器，来装载
        Map<Class, Object> container = new HashMap<>();
        ClassPath classPath = ClassPath.from(MySpringApplication.class.getClassLoader());
        List<Class<?>> componentClasses = classPath.getTopLevelClasses(MySpringApplication.class.getPackage().getName())
                .stream()
                .map(ClassPath.ClassInfo::getName)
                .map(name -> {
                    try {
                        return Class.forName(name);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .filter(klass -> klass.getAnnotation(MyController.class) != null || klass.getAnnotation(MyService.class)!=null)
                .collect(Collectors.toList());

        for (Class klass : componentClasses) {
            container.put(klass, klass.getConstructor().newInstance());
        }
        for (Object bean : container.values()) {
            for (Field field : bean.getClass().getDeclaredFields()) {
                if (field.getAnnotation(MyAutowired.class) != null) {
                    field.setAccessible(true);
                    field.set(bean, container.get(field.getType()));
                }
            }
        }
        System.out.println(container);

        // 启动自定义服务器
        MyServer.startServer(new MyDispatcherServlet(container));

    }
}

