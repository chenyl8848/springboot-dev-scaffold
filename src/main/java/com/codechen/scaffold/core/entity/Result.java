package com.codechen.scaffold.core.entity;

import com.codechen.scaffold.core.constant.ResultCodeEnum;
import lombok.Data;

/**
 * @author cyl
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

    public static <T> Result<T> success(Integer code, String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> success(String message, T data) {
        return success(ResultCodeEnum.SUCCESS.getCode(), message, data);
    }

    public static <T> Result<T> success(String message) {
        return success(message, null);
    }

    public static <T> Result<T> fail(Integer code, String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> fail(Integer code, String message) {
        return fail(code, message, null);
    }

    public static <T> Result<T> fail(String message, T data) {
        return fail(ResultCodeEnum.FAIL.getCode(), message, data);
    }

    public static <T> Result<T> fail(String message) {
        return success(message, null);
    }

}
