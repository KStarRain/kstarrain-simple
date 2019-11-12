package com.kstarrain.filter;


import javax.servlet.*;
import java.io.IOException;

/**
 * @author: Dong Yu
 * @create: 2018-10-17 17:43
 * @description:
 */
public class Test01Filter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        System.out.println("===============  初始化 Test01Filter 过滤器 ===============");

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        System.out.println("开始执行 Test01Filter 的doFilter方法");
        // Filter 只是链式处理，请求依然转发到目的地址。
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("结束执行 Test01Filter 的doFilter方法");
    }

    @Override
    public void destroy() {
        System.out.println("===============  销毁 Test01Filter 过滤器 ===============");
    }
}
