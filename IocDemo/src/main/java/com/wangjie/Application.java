package com.wangjie;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Application {
    public static void main(String[] args) {
        MyContainer myContainer = new MyContainer();
        myContainer.start();
        myContainer.getBean(AService.class);
    }
}

class MyContainer {

    private Map<String, Object> beans = new ConcurrentHashMap<>();

    public void start() {
        Properties properties = new Properties();
        // todo:反射完成Bean的初始化
    }

    public Object getBean(Class klass) {
        return null;
    }
}
