package com.kstarrain.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: Dong Yu
 * @create: 2018-10-16 17:19
 * @description:
 */
public class HttpClientController extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("===============  初始化 Test01Controller  ===============");
    }



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("开始执行 HttpClientController 的doGet方法");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //请求编码
        request.setCharacterEncoding("UTF-8");
        response.sendRedirect("test02");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Put request to TestController success");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Delete request to TestController success");
    }

    @Override
    public void destroy() {
        System.out.println("===============  销毁 Test01Controller ===============");
    }
}
