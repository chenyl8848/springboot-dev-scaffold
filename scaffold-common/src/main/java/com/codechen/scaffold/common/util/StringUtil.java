package com.codechen.scaffold.common.util;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;

/**
 * @author：Java陈序员
 * @date：2024-11-12 15:37
 * @description：字符串工具类
 */
public class StringUtil {
    /**
     * 下划线转大写 Hello_World --> HelloWorld
     *
     * @param name
     * @return
     */
    public static String convertToCamelCase(String name) {
        StringBuilder result = new StringBuilder();

        if (name == null || name.isEmpty()) {
            return "";
        }

        if (!name.contains("_")) {
            // 不包含下划线，仅将首字母大写
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }

        // 分割字符串
        String[] camels = name.split("_");
        for (String camel : camels) {
            if (!camel.isEmpty()) {
                // 首字母大写
                result.append(camel.substring(0, 1).toUpperCase());
                result.append(camel.substring(1).toLowerCase());
            }
        }

        return result.toString();
    }

    /**
     * 驼峰命名 created_at --> createdAt
     *
     * @param name
     * @return
     */
    public static String toCamelCase(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }

        name = name.toLowerCase();
        StringBuilder stringBuilder = new StringBuilder(name.length());
        boolean upperCase = false;

        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);

            if (c == '_') {
                upperCase = true;
            } else if (upperCase) {
                stringBuilder.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                stringBuilder.append(c);
            }
        }

        return stringBuilder.toString();
    }

    /**
     * 判断字符串数组是否包含某个元素
     *
     * @param arrays 字符串数组
     * @param key    关键字
     * @return
     */
    public static boolean arraysContains(String[] arrays, String key) {
        return Arrays.asList(arrays).contains(key);
    }

    /**
     * 分割字符串
     *
     * @param str   字符串
     * @param open  起始字符串
     * @param close 终止字符串
     * @return
     */
    public static String subStringBetween(String str, String open, String close) {
        if (!ObjectUtils.allNotNull(new Object[]{str, open, close})) {
            return null;
        }

        int start = str.indexOf(open);
        if (start != -1) {
            int end = str.indexOf(close);
            if (end != -1) {
                return str.substring(start + open.length(), end);
            }
        }

        return null;
    }
}
