package com.kstarrain.finally1;

/**
 * @author: DongYu
 * @create: 2019-03-05 18:00
 * @description:
 */
public class FinallyTest1 {

    public static void main(String[] args) {

        System.out.println(test11());
    }

    public static String test11() {
        try {
            System.out.println("try block");

            return test12();
        } finally {
            System.out.println("finally block");
        }
    }

    public static String test12() {
        System.out.println("return statement");

        return "after return";
    }

}
