package com.wangjie.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.*;


@Controller
public class MyController {

    @RequestMapping("/test")
    public void test(@RequestBody Map<String, Object> param) {
        System.out.println(param);
    }




}
