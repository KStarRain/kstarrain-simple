package com.kstarrain.controller;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: Dong Yu
 * @create: 2018-10-16 17:19
 * @description:
 */
public class CookieController extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("===============  初始化 CookieController  ===============");
    }



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        System.out.println("开始执行CookieController的doGet方法");
//        Cookie[] cookies = request.getCookies();
//        System.out.println("cookie 数量为:" + cookies.length);
//        if (cookies != null){
//            for (Cookie cookie : cookies) {
//                System.out.println(cookie.getName() + "     " + cookie.getValue());
//            }
//        }

        Cookie testCookie = new Cookie("testName","testValue");
        response.addCookie(testCookie);


        //响应编码
        response.setContentType("text/html;charset=utf-8");


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //请求编码
        request.setCharacterEncoding("UTF-8");
        response.sendRedirect("test02");
    }


    @Override
    public void destroy() {
        System.out.println("===============  销毁 CookieController ===============");
    }
}
