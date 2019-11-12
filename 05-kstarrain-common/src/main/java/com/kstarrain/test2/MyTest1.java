package com.kstarrain.test2;

import com.alibaba.fastjson.JSON;

/**
 * @author: DongYu
 * @create: 2019-03-01 19:00
 * @description:
 */
public class MyTest1 {

    public static void main(String[] args) {

     /*   ArrayList<Object> objects = new ArrayList<>();
        objects.add("");
        new ArrayList<>(10);
        
        User2 user2 = new User2();
        System.out.println(user2);

        User user = new User();
        System.out.println(user);*/

//        int aNull = Integer.parseInt("");
//        System.out.println(aNull);
//        System.out.println("XITC01".matches("XITC01"));
//        System.out.println("XITC01".matches("^(XITC01)$"));



        System.out.println(JSON.toJSONString(null));

//        System.out.println("XITC01".matches("[\\d\\D]*"));
    }

}
