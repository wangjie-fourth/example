package com.wangjie;

import com.wangjie.my.MyService;

@MyService
public class DemoService {

    public String sayHello(String name) {
        return "Hello " + name;
    }
}