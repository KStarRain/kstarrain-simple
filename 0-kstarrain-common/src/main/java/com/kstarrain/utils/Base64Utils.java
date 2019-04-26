package com.kstarrain.utils;

import org.apache.commons.codec.binary.Base64;

public class Base64Utils {

    private Base64Utils() {
        throw new IllegalStateException("Base64Utils class");
    }

    public static byte[] decode(String base64) {
        return Base64.decodeBase64(base64.getBytes());
    }

    public static String encode(byte[] bytes) {
        return new String(Base64.encodeBase64(bytes));
    }
}