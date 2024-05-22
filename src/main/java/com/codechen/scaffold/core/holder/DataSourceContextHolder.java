package com.codechen.scaffold.core.holder;

/**
 * @author：Java陈序员
 * @date 2023-04-28 11:29
 * @description 多数据源上下文工具类
 */
public class DataSourceContextHolder {

    private static final ThreadLocal<String> CACHE = new ThreadLocal<>();

    public static void setDataSourceType(String dataSourceType) {
        CACHE.set(dataSourceType);
    }

    public static String getDataSourceType() {
        return CACHE.get();
    }

    public static void clear() {
        CACHE.remove();
    }
}
