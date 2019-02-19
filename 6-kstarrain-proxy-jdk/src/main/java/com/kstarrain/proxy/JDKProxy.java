package com.kstarrain.proxy;

import com.kstarrain.annotation.Transactional;
import com.kstarrain.aspect.TransactionAspect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: DongYu
 * @create: 2019-01-31 12:57
 * @description: JDK动态代理
 *               参考文档：http://www.cnblogs.com/zby9527/p/6945756.html
 *                       https://blog.csdn.net/yhl_jxy/article/details/80635012
 */
public class JDKProxy {

    public static Object createTranactionProxy(final Object targetObj, final TransactionAspect transactionAspect){

        // 使用JDK的Proxy类为目标类创建代理对象
        Object proxy = Proxy.newProxyInstance(
                // 目标类使用的类加载器
                targetObj.getClass().getClassLoader(),
                // 目标类实现的接口
                targetObj.getClass().getInterfaces(),
                // 执行处理器，代理具体的业务逻辑
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        //判断是否有Transactional注解 (jdk中 method为接口中的方法 不是实现类的方法)
                        //这里使用的是jdk的动态代理,所以需要将注解放到接口上面,如果放到实现类的方法上面会导致读取不到
                        Transactional transactional = method.getDeclaredAnnotation(Transactional.class);

                        if (transactional != null) {
                            // 执行切面方法
                            transactionAspect.startTransaction();
                        }

                        // 具体逻辑代码执行,返回值为方法执行结果
                        Object result = method.invoke(targetObj, args);

                        if (transactional != null) {
                            transactionAspect.commitTransaction();
                        }

                        // 返回方法执行结果
                        return result;
                    }
                }


        );

        return proxy;
    }
}
