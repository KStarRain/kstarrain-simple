package com.kstarrain.basic;


import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author: Dong Yu
 * @create: 2018-11-13 15:52
 * @description:
 */


public class DateTest {


    @Test
    public void test01() throws ParseException {

        Date date1 = DateUtils.addDays(new Date(), -1);

        SimpleDateFormat df = new SimpleDateFormat("HH:mm");

        String a = "08:00";

        Date parse = df.parse(df.format(new Date()));

        Calendar date = Calendar.getInstance();
        date.setTime(parse);


        new SimpleDateFormat("HH:mm").parse(a);


    }


}
