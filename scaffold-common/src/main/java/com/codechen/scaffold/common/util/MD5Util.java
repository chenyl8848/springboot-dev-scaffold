package com.codechen.scaffold.common.util;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author：Java陈序员
 * @date 2023-04-06 17:29
 * @description MD5 加密工具类
 */
public class MD5Util {

    private static final String ALGORITHMS = "MD5";

    /**
     * MD5 加密
     *
     * @param content 明文
     * @param charset 字符
     * @return 摘要
     */
    public static String MD5(String content, Charset charset) {
        String md5 = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHMS);

            messageDigest.update(content.getBytes(charset));

            md5 = new BigInteger(1, messageDigest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return md5;
    }

    /**
     * MD5 加密
     *
     * @param content 明文
     * @param salt    随机盐
     * @param charset 字符
     * @return 摘要
     */
    public static String MD5(String content, String salt, Charset charset) {
        String md5 = "";

        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHMS);
            messageDigest.update(content.getBytes(charset));
            messageDigest.update(salt.getBytes(charset));

            md5 = new BigInteger(1, messageDigest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return md5;
    }

    /**
     * MD5 加密
     *
     * @param content 明文
     * @return 摘要
     */
    public static String MD5(String content) {
        return MD5(content, StandardCharsets.UTF_8);
    }

    /**
     * MD5 加密
     *
     * @param content 明文
     * @param salt    随机盐
     * @return 摘要
     */
    public static String MD5(String content, String salt) {
        return MD5(content, salt, StandardCharsets.UTF_8);
    }
}
