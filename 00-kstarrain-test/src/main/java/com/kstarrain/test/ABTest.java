package com.kstarrain.test;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: DongYu
 * @create: 2019-03-05 13:52
 * @description:
 */
public class ABTest {
    public static void main(String[] args) {
//        new TestB();

//        int a = 1 << 3;
//        System.out.println(a);
//
//        int b = 4 >> 1;
//        System.out.println(b);

        HashMap<Object, Object> map = new HashMap<>();
//        map.size();
        map.put("1","1");
        Set<Map.Entry<Object, Object>> entries = map.entrySet();


        ConcurrentHashMap<Object, Object> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("2","3");


        Hashtable<Object, Object> hashTable = new Hashtable<>();
        hashTable.put("3","4");

        map.put("1","2");


    }
}
