package com.kstarrain;

import com.kstarrain.aspect.TransactionAspect;
import com.kstarrain.proxy.JDKProxy;
import com.kstarrain.service.IStudentService;
import com.kstarrain.service.impl.StudentServiceImpl;
import org.junit.Test;

/**
 * @author: DongYu
 * @create: 2019-01-31 13:06
 * @description:  spring aop https://www.cnblogs.com/zhaozihan/p/5953063.html
 */
public class JDKProxyTest {

    @Test
    public void testJDKProxy() {

        /** 代理接口 */
        System.out.println("==========代理接口==========");
        IStudentService studentService = new StudentServiceImpl();
        System.out.println(studentService.getClass().getName());

        IStudentService proxyStudentService = (IStudentService) JDKProxy.createTranactionProxy(studentService, new TransactionAspect());
        System.out.println(proxyStudentService.getClass().getName());

        System.out.println("+++++++++++++++++++++++++++++++++++");
        proxyStudentService.insertStudent01();

        System.out.println("+++++++++++++++++++++++++++++++++++");
        proxyStudentService.insertStudent02();
    }


}
