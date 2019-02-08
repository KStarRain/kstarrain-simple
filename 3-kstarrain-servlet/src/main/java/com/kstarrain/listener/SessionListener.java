package com.kstarrain.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: Dong Yu
 * @create: 2018-10-22 10:34
 * @description:
 */
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

        HttpSession session = httpSessionEvent.getSession();
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " Session监听器  监听到Session创建   sessionId = " + session.getId());

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

        HttpSession session = httpSessionEvent.getSession();
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " Session监听器  监听到Session销毁   sessionId = " + session.getId());
    }
}
