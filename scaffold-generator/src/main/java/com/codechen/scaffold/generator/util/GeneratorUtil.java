package com.codechen.scaffold.generator.util;

import com.codechen.scaffold.generator.constant.GeneratorConstants;
import com.codechen.scaffold.common.util.StringUtil;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author：Java陈序员
 * @date：2024-11-6 11:29
 * @description：代码生成工具类
 */
public class GeneratorUtil {

    /**
     * 表名转类名
     *
     * @param tableName
     * @return
     */
    public static String convertClassName(String tableName, Boolean ignoreTablePrefix, String tablePrefix) {
        if (ignoreTablePrefix && StringUtils.isNotBlank(tablePrefix)) {
            if (tableName.startsWith(tablePrefix)) {
                tableName = tableName.replace(tablePrefix, "");
            }
        }
        return StringUtil.convertToCamelCase(tableName);
    }

    /**
     * 列名转属性
     *
     * @param columnName
     * @return
     */
    public static String convertFieldName(String columnName) {
        return StringUtil.toCamelCase(columnName);
    }

    /**
     * 列类型转字段类型
     *
     * @param dataType   数据类型
     * @param columnType 列类型
     * @return 字段类型
     */
    public static String convertFieldType(String dataType, String columnType) {

        if (StringUtil.arraysContains(GeneratorConstants.COLUMN_TYPE_STRING, dataType) ||
                StringUtil.arraysContains(GeneratorConstants.COLUMN_TYPE_TEXT, dataType)) {
            return GeneratorConstants.FIELD_TYPE_STRING;
        }

        if (StringUtil.arraysContains(GeneratorConstants.COLUMN_TYPE_NUMBER, dataType)) {
            String scope = StringUtil.subStringBetween(columnType, "(", ")");
            String[] scopeArray = scope.split(",");

            if (scopeArray != null && scopeArray.length == 2 && Integer.parseInt(scopeArray[1]) > 0) {
                // 浮点型
                return GeneratorConstants.FIELD_TYPE_BIGDECIMAL;
            }

            if (scopeArray != null && scopeArray.length == 1 && Integer.parseInt(scopeArray[0]) <= 11) {
                // 整型
                return GeneratorConstants.FIELD_TYPE_INTEGER;
            }

            // 长整型
            return GeneratorConstants.FIELD_TYPE_LONG;
        }

        if (StringUtil.arraysContains(GeneratorConstants.COLUMN_TYPE_TIME, dataType)) {
            return GeneratorConstants.FIELD_TYPE_DATE;
        }

        return "String";
    }

    /**
     * 代码创建时间
     *
     * @param localDateTime
     * @param pattern
     * @return
     */
    public static String convertCreateTime(LocalDateTime localDateTime, String pattern) {
        if (null == localDateTime) {
            localDateTime = LocalDateTime.now();
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);

        return dateTimeFormatter.format(localDateTime);
    }

}
