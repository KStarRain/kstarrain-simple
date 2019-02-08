package com.kstarrain;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author: Dong Yu
 * @create: 2018-10-29 14:28
 * @description:
 */
public class Log4jController {

    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger(Log4jController.class);

        for (int i = 1; i <= 108; i++) {
            logger.trace("slf4j print trace :"+ i);
            logger.debug("slf4j print debug :"+ i);
            logger.info("slf4j print info :"+ i);
            logger.warn("slf4j print warn :"+ i);
        }

        try {
            throw new RuntimeException("异常");
        } catch (RuntimeException e) {
            logger.error(e.getMessage(),e);
        }


    }




}
