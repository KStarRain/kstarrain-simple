package com.kstarrain.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author: Dong Yu
 * @create: 2018-10-16 17:19
 * @description:
 *
 * 用户打开浏览器访问服务器的某一应用资源后，如 SessionController（用户向服务器的一个Servlet发起了请求）。
 * 在该Servlet中服务器调用了request.getSession()方法,则服务器将会判断针对当次会话是否创建了Session
 * 判断依据是先判断cookie中有没有JSESSIONID = {sessionId}，根据这个sessionId作为key去查找Session
 * 如果没有则在服务器中创建一个Session并生成一个唯一标识，我们称之为sessionId
 * 并在response时将该信息设置到Cookie中，该Cookie的名称为JSESSIONID
 * Set-Cookie: JSESSIONID=3DD2BB1FE7376B2FEFAAEAE24832FD52; Path=/; HttpOnly
 * 默认当浏览器关闭时该Cookie将被销毁。
 * 浏览器端通过获取到该Cookie信息后，用户下次再发起请求时（可以请求与上一次不同的Web资源），则将携带Cookie信息到服务器，
 * 服务器通过该Cookie就可以根据Session ID得知当前会话保存的Session。上次请求操作保存在session中的信息，
 * 在下次请求操作也同样可以读取到session中保存的信息。
 */
public class Session01Controller extends HttpServlet {

    @Override
    public void init() throws ServletException {

        System.out.println("===============  初始化 Session01Controller  ===============");

    }



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        System.out.println("===============  开始执行Session01Controller的 doGet方法 " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//
//        Cookie[] cookies = request.getCookies();
//
//        if (cookies != null){
//            System.out.println("request 中 Cookie 数量为:" + cookies.length);
//            for (Cookie cookie : cookies) {
//                System.out.println("   Cookie :   " + cookie.getName() + " = " + cookie.getValue());
//            }
//        }else{
//            System.out.println("request 中 cookie 数量为:" + 0);
//        }
//
//        System.out.println("开始创建/获取 Session ");
        HttpSession session = request.getSession();
//        String sessionId = session.getId();
//        System.out.println("   Session :    sessionId = " + sessionId);
//
//        System.out.println("设置 Session 属性  username = 董宇");
//        session.setAttribute("username","董宇");
//        System.out.println("设置 Session 属性  password = 123456");
//        session.setAttribute("password","123456");
//
//        String setcookie = response.getHeader("Set-Cookie");
//        System.out.println("response 的 header 中  Set-Cookie : " + setcookie);



    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }


    @Override
    public void destroy() {
        System.out.println("===============  销毁 Session01Controller ===============");
    }
}
