package com.codechen.scaffold.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author：Java陈序员
 * @date 2023-06-26 10:41
 * @description ThreadLocal 工具类
 */
public class ThreadLocalUtil {

    private static ThreadLocal<Map<String, Object>> local = new InheritableThreadLocal<Map<String, Object>>() {
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<>(16);
        }
    };

    public static <T> void set(String key, T value) {
        local.get().put(key, value);
    }

    public static <T> T get(String key) {
        return (T) local.get().get(key);
    }

    public static void remove(String key) {
        local.get().remove(key);
    }

    public static void remove() {
        local.remove();
    }
}
