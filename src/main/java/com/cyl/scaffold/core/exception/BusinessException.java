package com.cyl.scaffold.core.exception;

import com.cyl.scaffold.core.constant.ResultCodeEnum;
import lombok.Data;

/**
 * @author cyl
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
