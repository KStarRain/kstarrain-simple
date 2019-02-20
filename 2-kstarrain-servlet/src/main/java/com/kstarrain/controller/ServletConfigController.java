package com.kstarrain.controller;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * @author: Dong Yu
 * @create: 2018-10-16 17:19
 * @description:
 */
public class ServletConfigController extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("===============  初始化 ServletConfigController  ===============");
    }



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServletConfig servletConfig = this.getServletConfig();

        //该servlet的名字
        String servletName = servletConfig.getServletName();
        System.out.println(servletName);

        //全局context
        ServletContext servletContext = servletConfig.getServletContext();
        System.out.println(servletContext);
        Enumeration<String> initContextParameterNames = servletContext.getInitParameterNames();
        while (initContextParameterNames.hasMoreElements()){
            String initContextParameterName = initContextParameterNames.nextElement();
            String initContextParameterValue = servletContext.getInitParameter(initContextParameterName);
            System.out.println(initContextParameterName + " ---- " + initContextParameterValue);
        }


        //该servlet的init值
        Enumeration<String> initParameterNames = servletConfig.getInitParameterNames();
        while (initParameterNames.hasMoreElements()){
            String initParameterName = initParameterNames.nextElement();
            String initParameterValue = servletConfig.getInitParameter(initParameterName);
            System.out.println(initParameterName + " ---- " + initParameterValue);
        }
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
