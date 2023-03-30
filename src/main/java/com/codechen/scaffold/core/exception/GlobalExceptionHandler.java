package com.codechen.scaffold.core.exception;

import com.codechen.scaffold.core.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author cyl
 * @date 2023-03-28 17:14
 * @description 全局异常处理类
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = GlobalException.class)
    public Result handleGlobalException(GlobalException exception) {
        logger.error(exception.getMessage());
        return Result.fail(exception.getCode(), exception.getMessage(), null);
    }
}
