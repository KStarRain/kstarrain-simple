package com.kstarrain.proxy;

import com.kstarrain.annotation.Transactional;
import com.kstarrain.aspect.TransactionAspect;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author: DongYu
 * @create: 2019-01-31 12:57
 * @description: JDK动态代理
 *               参考文档：http://www.cnblogs.com/zby9527/p/6945756.html
 *                       https://blog.csdn.net/yhl_jxy/article/details/80635012
 */
public class CGLIBProxy {

    public static Object createTranactionProxy(final Object targetObj, final TransactionAspect transactionAspect){
        // 构建一个cglib的增强器
        Enhancer enhancer = new Enhancer();
        // 设置父类为需要代理的类
        enhancer.setSuperclass(targetObj.getClass());
        // 设置回调 (执行处理器，代理具体的业务逻辑)
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

                //判断是否有Transactional注解 (cgib中 如果代理的为接口的话，method为实现类的方法)
                Transactional transactional = method.getDeclaredAnnotation(Transactional.class);

                if (transactional != null) {
                    // 执行切面方法
                    transactionAspect.startTransaction();
                }

                // 具体逻辑代码执行,返回值为方法执行结果
//                Object result = method.invoke(targetObj, args);
                Object result = methodProxy.invokeSuper(proxy, args);

                if (transactional != null) {
                    transactionAspect.commitTransaction();
                }
                // 返回方法执行结果
                return result;
            }
        });
        // 创建代理
        return enhancer.create();
    }
}
