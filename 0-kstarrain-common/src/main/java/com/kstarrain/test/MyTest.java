package com.kstarrain.test;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author: DongYu
 * @create: 2019-03-01 19:00
 * @description:
 */
public class MyTest {

    public static void main(String[] args) {

        ArrayList<Object> objects = new ArrayList<>();
        objects.size();
        objects.add("");
        new ArrayList<>(10);

        new LinkedList<>();
        new LinkedList<>();

        User2 user2 = new User2();
        user2.count = 1;
        user2.sex = "女";
        System.out.println(user2);

        User user = new User();
        user.count = 1;
        user.sex = "男";
        System.out.println(user);


    }

}
