package com.kstarrain;

import com.kstarrain.pojo.Student;

import java.lang.reflect.InvocationTargetException;

/**
 * @author: DongYu
 * @create: 2019-03-05 19:15
 * @description:
 */
public class RefixTest {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

//        Class<?> clazz = Class.forName("com.kstarrain.pojo.Student");
//        Student student = (Student) clazz.newInstance();
//        Method setIdMethod = clazz.getMethod("setId", String.class);
//        setIdMethod.invoke(student,"1");


        System.out.println(Student.class.getName());
//        System.out.println(student);

    }
}
