package com.kstarrain;

import com.kstarrain.aspect.TransactionAspect;
import com.kstarrain.proxy.CGLIBProxy;
import com.kstarrain.service.IStudentService;
import com.kstarrain.service.impl.StudentService;
import com.kstarrain.service.impl.StudentServiceImpl;
import org.junit.Test;

/**
 * @author: DongYu
 * @create: 2019-01-31 13:53
 * @description:
 */
public class CGLIBProxyTest {

    @Test
    public void testCGLIBProxy() {

        /** 代理类 */
        System.out.println("==========代理类==========");
        StudentService studentService01 = new StudentService();
        System.out.println(studentService01.getClass().getName());
        StudentService proxyStudentService01 = (StudentService) CGLIBProxy.createTranactionProxy(studentService01, new TransactionAspect());
        System.out.println(proxyStudentService01.getClass().getName());

        System.out.println("+++++++++++++++++++++++++++++++++++");
        proxyStudentService01.insertStudent01();

        System.out.println("+++++++++++++++++++++++++++++++++++");
        proxyStudentService01.insertStudent02();


        /** 代理接口 */
        System.out.println("");
        System.out.println("==========代理接口==========");
        IStudentService studentService02 = new StudentServiceImpl();
        System.out.println(studentService02.getClass().getName());
        IStudentService proxyStudentService02 = (IStudentService) CGLIBProxy.createTranactionProxy(studentService02, new TransactionAspect());
        System.out.println(proxyStudentService02.getClass().getName());

        System.out.println("+++++++++++++++++++++++++++++++++++");
        proxyStudentService02.insertStudent01();

        System.out.println("+++++++++++++++++++++++++++++++++++");
        proxyStudentService02.insertStudent02();
    }
}
