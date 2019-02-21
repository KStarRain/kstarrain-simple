package com.kstarrain.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: DongYu
 * @create: 2019-02-21 10:50
 * @description: 本地线程工具类
 */
@Slf4j
public class ThreadLocalUtils {

    private static final ThreadLocal<Map<Object, Object>> threadBucket = new InheritableThreadLocalMap<Map<Object, Object>>();

    public static <T> T getValue(Object key, Class<T> clazz) {
        Map<Object, Object> bucket = threadBucket.get();
        if (null == bucket) {
            if ( log.isWarnEnabled() ) {
                log.warn("ThreadLocal bucket: " + key + " not exists.");
            }
            return null;
        }
        Object valueSrc = bucket.get(key);
        if (clazz.isInstance(valueSrc)) {
            return (T) valueSrc;
        } else {
            if ( log.isWarnEnabled() ) {
                if ( null == valueSrc ) {
                    log.warn("Cannot find object from ThreadLocal bucket: " + key
                            + "; no object found");
                } else {
                    log.warn("Cannot find object from ThreadLocal bucket: " + key
                            + "; except type is " + clazz.getSimpleName()
                            + " but got " + valueSrc.getClass().getSimpleName());
                }
            }
            return null;
        }
    }

    public static <T> void setValue(Object key, T value) {
        Map<Object, Object> bucket = threadBucket.get();
        if (null == bucket) {
            Map<Object, Object> newBucket = new HashMap<Object, Object>();
            threadBucket.set(newBucket);
            bucket = newBucket;
        }
        bucket.put(key, value);
    }

    public static void clear(Object key) {
        Map<Object, Object> bucket = threadBucket.get();
        if (null == bucket) {
            return;
        }
        bucket.remove(key);
    }

    public static void clear() {
        if(threadBucket !=null ){
            threadBucket.remove();
        }
    }

    private static final class InheritableThreadLocalMap<T extends Map<Object, Object>> extends InheritableThreadLocal<Map<Object, Object>> {

        protected Map<Object, Object> childValue(Map<Object, Object> parentValue) {
            if (parentValue != null) {
                return (Map<Object, Object>) ((HashMap<Object, Object>) parentValue).clone();
            } else {
                return null;
            }
        }
    }

}
