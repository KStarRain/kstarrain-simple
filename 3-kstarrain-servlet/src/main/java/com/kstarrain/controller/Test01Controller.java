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
public class Test01Controller extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("===============  初始化 Test01Controller  ===============");
    }



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("开始执行 Test01Controller 的doGet方法");
/*      tomcat8以后请求默认UTF-8,如果是tomcat8以下版本，建议修改tomcat中的配置 server.xml
        <Connector port="8080" protocol="HTTP/1.1" connectionTimeout="20000" redirectPort="8443" URIEncoding="UTF-8"/>*/
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //http://localhost:8080/test01?username=%E8%91%A3%E5%AE%87&password=123456
        System.out.println(username + " " + password);

        //响应编码
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().append(username + " " +  password);

        //设置每隔3秒就自动刷新一次
//        response.setHeader("Refresh","1");
//        response.getWriter().print(new Date().getSeconds());



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
