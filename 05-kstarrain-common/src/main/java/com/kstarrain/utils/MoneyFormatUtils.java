package com.kstarrain.utils;

import java.text.DecimalFormat;

/**
 * @author: DongYu
 * @create: 2019-12-09 17:14
 * @description:
 */
public class MoneyFormatUtils {


    private static final DecimalFormat DF = new DecimalFormat("#,##0.00");

    public static String format(Double obj) {
        if (obj != null) {
            return DF.format(obj);
        } else {
            return null;
        }
    }
}
