package com.kstarrain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * @author: Dong Yu
 * @create: 2018-10-29 14:28
 * @description:
 */
public class LogbackController {

    public static void main(String[] args) {

        Logger log = LoggerFactory.getLogger(LogbackController.class);
        MDC.put("user", "153");


        log.debug("logback print debug ");

//        for (int i = 1; i <= 108; i++) {
//            log.trace("logback print trace :"+ i);
//            log.debug("logback print debug :"+ i);
//            log.info("logback print info :"+ i);
//            log.warn("logback print warn :"+ i);
//        }
//
//        try {
//            throw new RuntimeException("异常");
//        } catch (RuntimeException e) {
//            log.error(e.getMessage(),e);
//        }


    }




}
