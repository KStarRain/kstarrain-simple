package com.kstarrain;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: Dong Yu
 * @create: 2019-03-21 15:58
 * @description:
 */
public class RegexTest {

    @Test
    public void test1(){

        String line = "110000     北京市";
        Matcher postcodeMatcher = Pattern.compile("([0-9]+)").matcher(line);
        Matcher nameMatcher = Pattern.compile("([\u4e00-\u9fa5]+)").matcher(line);
        boolean b1 = postcodeMatcher.find();
        boolean b2 = nameMatcher.find();
        if (b1 && b2) {
            String postCode = postcodeMatcher.group(1);
            String name = nameMatcher.group(1);

            System.out.println();
        }
    }

    @Test
    public void test2() {

        String regex = "^(?!(CQXD|CIMS|AVIC01|XITC01|ZRT01|YXITC01)).*$";
        regex = "BoHai|LHFK02|LHFK03|LHFK04|LHFK05|LHFK06|LHFK07|LHFK08|LHFK09|SMABS|Syndicated|YLDZ";

        System.out.println("BOHAI".matches(regex));

    }


    @Test
    public void test3() {

        String regex = "1[3-9]\\d{9}$";

        System.out.println("13836843570".matches(regex));

    }


    @Test
    public void test4() {

        System.out.println("ZRT01".matches("[Z][R][T]\\w*$"));
//        Object a = null;
//
//        if(a instanceof String){
//            System.out.println(111);
//        }
//        String regex = "BoHai|[A][V][I][C][0-9]{0,2}$|[L][H][F][K]\\d*$";
//
//        System.out.println("EB03".matches("[E][B][0-9]{2}$"));



/*
        System.out.println("AVIC01".matches(regex));

        System.out.println("XITC01".matches("^X(?!1TC011)*$"));

        System.out.println("XITC01".matches("^(.)*$"));
        System.out.println("XITC01".matches("^.*$"));

        System.out.println("XITC01".matches("^(?!(XITC011))$"));

        System.out.println("XITC01".matches("^((?!((CQXD)|(CIMS)|(AVIC01)|(XITC01))).)*$"));
        System.out.println("XITC0".matches("^((?!(XITC01)).)*$"));

        System.out.println("XITC02".matches("^(?!(CQXD|AVIC01|XITC01)).*$"));

        */

    }


    @Test
    public void test5() {

        System.out.println("市".matches("[^市].*"));
        System.out.println("北京市".matches("[^市].*"));
        System.out.println("黑龙江省".matches("([^市]+市).*"));
        System.out.println("黑龙江省大庆市".matches("([^市]+市).*"));
        System.out.println("上海市上海市".matches("([^市]+市).*"));
        System.out.println("内蒙古自治区呼和浩特市新城区".matches("([^市]+市|.*?自治州).*"));


    }
}
