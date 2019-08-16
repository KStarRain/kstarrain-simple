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
}
