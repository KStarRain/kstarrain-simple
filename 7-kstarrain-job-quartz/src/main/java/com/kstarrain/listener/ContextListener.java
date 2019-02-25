package com.kstarrain.listener;

import com.kstarrain.config.QuartzConfig;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author: DongYu
 * @create: 2019-02-22 16:46
 * @description: quartz定时任务监听器，tomcat启动时加载所有作业
 */
@Slf4j
public class ContextListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        //容器初始化时，加载quartz配置
        QuartzConfig.init();
    }


    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

        //容器销毁时，销毁quartz配置(清理线程工作)
        QuartzConfig.destroyed();

    }
}
