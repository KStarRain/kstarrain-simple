package com.kstarrain;

import org.junit.Test;

/**
 * @author: Dong Yu
 * @create: 2019-03-21 15:58
 * @description:
 */
public class ParamTest {

    @Test
    public void test1(){

//        System.out.println(8 >> 2 );
       /* String line = "110000     北京市";
        Matcher postcodeMatcher = Pattern.compile("([0-9]+)").matcher(line);
        Matcher nameMatcher = Pattern.compile("([\u4e00-\u9fa5]+)").matcher(line);
        boolean b1 = postcodeMatcher.find();
        boolean b2 = nameMatcher.find();
        if (b1 && b2) {
            String postCode = postcodeMatcher.group(1);
            String name = nameMatcher.group(1);

            System.out.println();
        }*/

        ParamTest paramTest = new ParamTest();
        int i = 0;
        paramTest.abc(i);
        i = i ++;
        System.out.println(i);
    }

    private void abc(int i) {
        i ++;
    }

}
