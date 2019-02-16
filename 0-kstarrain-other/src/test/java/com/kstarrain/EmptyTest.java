package com.kstarrain;


import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import java.util.*;

/**
 * @author: Dong Yu
 * @create: 2018-11-13 15:52
 * @description:
 */


public class EmptyTest {


    @Test
    public void testString(){
        String str = null;
//        str = "";
//        str = " ";
//        System.out.println(str.isEmpty());
        System.out.println("-------------------------");
        System.out.println(StringUtils.isBlank(str));
    }

    @Test
    public void testList(){
        List<String> list = null;
//        list = new ArrayList();
//        list.add("1");
//        System.out.println(list.isEmpty());
        System.out.println("-------------------------");
        System.out.println(CollectionUtils.isEmpty(list));
    }

    @Test
    public void testSet(){
        Set<String> set = null;
//        set = new HashSet<>();
//        set.add("1");
//        System.out.println(set.isEmpty());
        System.out.println("-------------------------");
        System.out.println(CollectionUtils.isEmpty(set));
    }

    @Test
    public void testMap(){
        Map<String,String> map = null;
//        map = new HashMap<>();
//        map.put("1","貂蝉");
//        System.out.println(map.isEmpty());
        System.out.println("-------------------------");
        System.out.println(MapUtils.isEmpty(map));
    }




}
