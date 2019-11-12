package com.kstarrain.utils;

import com.kstarrain.exception.BusinessException;
import com.kstarrain.exception.ErrorCode;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;


public class ValidateUtils {

    public static void state(boolean expression, ErrorCode errorCode) {
        if (!expression) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * 校验表达式为false(如果为false无操作；如果为true抛异常)
     * @param expression
     * @param errorCode
     */
    public static void isFalse(boolean expression, ErrorCode errorCode) {
        if (expression) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * 校验表达式为true(如果为true无操作；如果为false抛异常)
     * @param expression
     * @param errorCode
     */
    public static void isTrue(boolean expression, ErrorCode errorCode) {
        if (!expression) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * 校验对象为null(如果为null无操作；如果不为null抛异常)
     * @param object
     * @param errorCode
     */
    public static void isNull(Object object, ErrorCode errorCode) {
        if (object != null) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * 校验对象为null(如果为null无操作；如果不为null抛异常)
     * @param object
     * @param errorCode
     */
    public static void notNull(Object object, ErrorCode errorCode) {
        if (object == null) {
            throw new BusinessException(errorCode);
        }
    }

    public static void isEmpty(byte[] array, ErrorCode errorCode) {
        if (ArrayUtils.isNotEmpty(array)) {
            throw new BusinessException(errorCode);
        }
    }

    public static void notEmpty(byte[] array, ErrorCode errorCode) {
        if (ArrayUtils.isEmpty(array)) {
            throw new BusinessException(errorCode);
        }
    }

    public static void isEmpty(Object[] array, ErrorCode errorCode) {
        if (ArrayUtils.isNotEmpty(array)) {
            throw new BusinessException(errorCode);
        }
    }

    public static void notEmpty(Object[] array, ErrorCode errorCode) {
        if (ArrayUtils.isEmpty(array)) {
            throw new BusinessException(errorCode);
        }
    }

    public static void isEmpty(Collection<?> collection, ErrorCode errorCode) {
        if (CollectionUtils.isNotEmpty(collection)) {
            throw new BusinessException(errorCode);
        }
    }

    public static void notEmpty(Collection<?> collection, ErrorCode errorCode) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BusinessException(errorCode);
        }
    }

    public static void isEmpty(Map<?, ?> map, ErrorCode errorCode) {
        if (MapUtils.isNotEmpty(map)) {
            throw new BusinessException(errorCode);
        }
    }

    public static void notEmpty(Map<?, ?> map, ErrorCode errorCode) {
        if (MapUtils.isEmpty(map)) {
            throw new BusinessException(errorCode);
        }
    }

    public static void isEmpty(String text, ErrorCode errorCode) {
        if (StringUtils.isNotEmpty(text)) {
            throw new BusinessException(errorCode);
        }
    }

    public static void notEmpty(String text, ErrorCode errorCode) {
        if (StringUtils.isEmpty(text)) {
            throw new BusinessException(errorCode);
        }
    }

    public static void isBlank(String text, ErrorCode errorCode) {
        if (StringUtils.isNotBlank(text)) {
            throw new BusinessException(errorCode);
        }
    }

    public static void notBlank(String text, ErrorCode errorCode) {
        if (StringUtils.isBlank(text)) {
            throw new BusinessException(errorCode);
        }
    }

    public static void isContain(String textToSearch, String substring, ErrorCode errorCode) {
        if (!textToSearch.contains(substring)) {
            throw new BusinessException(errorCode);
        }
    }

    public static void notContain(String textToSearch, String substring, ErrorCode errorCode) {
        if (textToSearch.contains(substring)) {
            throw new BusinessException(errorCode);
        }
    }

    public static void isAssignable(Class<?> superType, Class<?> subType, ErrorCode errorCode) {
        if (subType == null || !superType.isAssignableFrom(subType)) {
            throw new BusinessException(errorCode);
        }
    }

    public static void isInstanceOf(Class<?> type, Object object, ErrorCode errorCode) {
        if (!type.isInstance(object)) {
            throw new BusinessException(errorCode);
        }
    }

    public static void noNullElements(Object[] array, ErrorCode errorCode) {
        for (Object element : array) {
            if (element == null) {
                throw new BusinessException(errorCode);
            }
        }
    }

}
