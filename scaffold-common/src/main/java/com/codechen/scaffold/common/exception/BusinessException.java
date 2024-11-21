package com.codechen.scaffold.common.exception;

import com.codechen.scaffold.common.enums.ResultCodeEnum;
import lombok.Data;

/**
 * @author：Java陈序员
 * @date 2023-06-15 10:56
 * @description 业务异常
 */
@Data
public class BusinessException extends RuntimeException {

    private Integer code;

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "BusinessException{" +
                "code=" + code +
                "message=" + this.getMessage() +
                '}';
    }
}
