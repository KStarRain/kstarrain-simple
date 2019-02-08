package com.kstarrain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: Dong Yu
 * @create: 2018-10-29 14:28
 * @description:
 */
public class LogbackController {

    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger(LogbackController.class);

        for (int i = 1; i <= 108; i++) {
            logger.trace("logback print trace :"+ i);
            logger.debug("logback print debug :"+ i);
            logger.info("logback print info :"+ i);
            logger.warn("logback print warn :"+ i);
        }

        try {
            throw new RuntimeException("异常");
        } catch (RuntimeException e) {
            logger.error(e.getMessage(),e);
        }


    }




}
