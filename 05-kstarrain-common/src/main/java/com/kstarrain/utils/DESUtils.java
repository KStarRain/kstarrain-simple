package com.kstarrain.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

/**
 * DES加密
 * 注意：DES加密和解密过程中，密钥长度都必须是8的倍数
 */
public class DESUtils {
    private static final Logger logger = LoggerFactory.getLogger(DESUtils.class);

    private DESUtils() {
        throw new IllegalStateException("DESUtils class");
    }

    /**
     * 加密
     *
     * @param datasource byte[]
     * @param key   String
     * @return byte[]
     */
    public static byte[] encrypt(byte[] datasource, String key) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            //Cipher do encryption
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            return cipher.doFinal(datasource);
        } catch (Exception e) {
            logger.error("Failed to encrypt", e);
        }
        return null;
    }

    /**
     * 解密
     *
     * @param src      byte[]
     * @param password String
     * @return byte[]
     * @throws Exception
     */
    public static byte[] decrypt(byte[] src, String password) {
        try {
            // DES need random
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, securekey, random);
            return cipher.doFinal(src);
        } catch (Exception e) {
            logger.error("Failed to decrypt", e);
        }
        return new byte[0];
    }
}