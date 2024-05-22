package com.codechen.scaffold.core.entity;

import com.codechen.scaffold.core.constant.ResultCodeEnum;
import lombok.Data;

/**
 * @author：Java陈序员
 * @date 2023-03-28 16:50
 * @description 全局返回
 */
@Data
public class Result<T> {

    /** 返回码 */
    private Integer code;

    /** 返回信息 */
    private String message;

    /** 返回数据 */
    private T data;

    // 构造函数私有化
    private Result() {

    }

    public static <T> Result<T> build(Integer code, String message, T data) {
        Result<T> result = new Result<>();

        if (data != null) {
            result.setData(data);
        }

        result.setCode(code);
        result.setMessage(message);

        return result;
    }

    public static <T> Result<T> build(ResultCodeEnum resultCodeEnum, T data) {
        return build(resultCodeEnum.getCode(), resultCodeEnum.getMessage(), data);
    }

    public static <T> Result<T> success(T data) {
        return build(ResultCodeEnum.SUCCESS, data);
    }

    public static <T> Result<T> success(Integer code, String message) {
        return fail(code, message, null);
    }

    public static <T> Result<T> success(Integer code, String message, T data) {
        return build(code, message, data);
    }

    public static <T> Result<T> fail(T data) {
        return build(ResultCodeEnum.FAIL, data);
    }

    public static <T> Result<T> fail(Integer code, String message) {
        return fail(code, message, null);
    }

    public static <T> Result<T> fail(Integer code, String message, T data) {
        return build(code, message, data);
    }

}
