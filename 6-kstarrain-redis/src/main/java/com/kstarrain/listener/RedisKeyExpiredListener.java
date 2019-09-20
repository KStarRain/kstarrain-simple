package com.kstarrain.listener;

import com.kstarrain.runnable.KeyExcepiredHandleRunnable;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisPubSub;

/**
 * @author: Dong Yu
 * @create: 2019-09-18 08:40
 * @description: 对于redis的key失效监听器 参考文档 https://www.jianshu.com/p/f5f81b2b2005
 */
@Slf4j
public class RedisKeyExpiredListener extends JedisPubSub {

    /** 取得订阅的消息后的处理 */
    @Override
    public void onMessage(String channel, String message) {

        String key = message;

        log.info("取得订阅的消息后的处理 channel:{}, message:{}", channel, message);

        if (message.startsWith("LISTENER_")){
            new Thread(new KeyExcepiredHandleRunnable(key)).start();
        }

    }


    /** 初始化订阅时候的处理 */
    public void onSubscribe(String channel, int subscribedChannels) {
        log.info("初始化订阅时候的处理 channel:{}, subscribedChannels:{}", channel, subscribedChannels);
    }

    /** 取消订阅时候的处理 */
    public void onUnsubscribe(String channel, int subscribedChannels) {
        log.info("取消订阅时候的处理 channel:{}, subscribedChannels:{}", channel, subscribedChannels);
    }

    /** 初始化按表达式的方式订阅时候的处理 */
    public void onPSubscribe(String pattern, int subscribedChannels) {
        log.info("初始化按表达式的方式订阅时候的处理 pattern:{}, subscribedChannels:{}", pattern, subscribedChannels);
    }

    /** 取消按表达式的方式订阅时候的处理 */
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        log.info("取消按表达式的方式订阅时候的处理 pattern:{}, subscribedChannels:{}", pattern, subscribedChannels);
    }

    /** 取得按表达式的方式订阅的消息后的处理 */
    public void onPMessage(String pattern, String channel, String message) {
        log.info("取得按表达式的方式订阅的消息后的处理 pattern:{}, channel:{}, message:{}",pattern, channel, message);
    }

}
