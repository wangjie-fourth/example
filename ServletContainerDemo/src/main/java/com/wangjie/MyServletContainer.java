package com.wangjie;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class MyServletContainer {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ServletException, IOException {
        // 读取web.xml中的servlet-class
        String servletClass = readServerClassFromWebXml();
        //
        Class klass = Class.forName(servletClass);
        //
        HttpServlet servlet = (HttpServlet) klass.getConstructor().newInstance();
        servlet.init();

        // 读取端口上的TCP字节流
        HttpServletRequest request = readTCPBytes();
        HttpServletResponse response = initResponse();
        servlet.service(request, response);
    }

    private static HttpServletResponse initResponse() {
        return null;
    }

    private static HttpServletRequest readTCPBytes() {
        return null;
    }

    private static String readServerClassFromWebXml() {
        return "com.wangjie.MyServlet";
    }
}
