package com.kstarrain.test;

/**
 * @author: DongYu
 * @create: 2019-03-01 19:14
 * @description:
 */
public class User {

    private String name;

    final int age = 0;

    protected String sex;

    transient int count;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", count=" + count +
                '}';
    }
}
