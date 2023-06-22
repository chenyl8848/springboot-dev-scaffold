package com.cyl.scaffold.core.exception;

import com.cyl.scaffold.core.constant.ResultCodeEnum;
import lombok.Data;

/**
 * @author cyl
 * @date 2023-03-28 16:49
 * @description 全局异常
 */
@Data
public class GlobalException extends RuntimeException {

    private Integer code = ResultCodeEnum.FAIL.getCode();
    private String message;

    public GlobalException(String message) {
        super(message);
        this.message = message;
    }

    public GlobalException(String message, Throwable throwable) {
        super(message, throwable);
        this.message = message;
    }

    public GlobalException(Integer code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public GlobalException(Integer code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
        this.message = message;
    }
}
