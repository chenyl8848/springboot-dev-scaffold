package com.codechen.scaffold.core.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Random;

/**
 * @author：Java陈序员
 * @date 2023-05-09 17:04
 * @description random 工具类
 */
public class RandomUtil {

    private static final String FIX_STRING = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static Random random;

    // 使用双重检查机制来实现单例模式
    public static Random getRandom() {
        if (random == null) {
            synchronized (RandomUtil.class) {
                if (random == null) {
                    random = new Random();
                }
            }
        }
        return random;
    }

    /**
     * 从固定字符串中随机挑选一定长度的字符串
     *
     * @param fixString 固定字符串
     * @param length    返回的字符串长度
     * @return
     */
    public static String getRandomString(String fixString, Integer length) {

        if (StringUtils.isBlank(fixString)) {
            return "";
        }

        if (length == null || length < 1) {
            return "";
        }

        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = getRandom().nextInt(fixString.length());
            stringBuffer.append(fixString.charAt(number));
        }

        return stringBuffer.toString();
    }

    /**
     * 从固定字符串中随机挑选一定长度的字符串
     *
     * @param length 返回的字符串长度
     * @return
     */
    public static String getRandomString(Integer length) {

        return getRandomString(FIX_STRING, length);
    }
}
