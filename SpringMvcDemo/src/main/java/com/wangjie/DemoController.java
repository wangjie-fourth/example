package com.wangjie;

import com.wangjie.my.*;

import java.util.Arrays;

@MyController
public class DemoController {
    @MyAutowired
    private DemoService demoService;

    @MyGetMapping("/hello")
    public String index(@MyRequestParam("name")String name) {
        return demoService.sayHello(name);
    }

    @MyGetMapping("/helloJson")
    @MyResponseBody
    public Object indexJson(@MyRequestParam("name")String name) {
        return demoService.sayHello(name);
    }

}
