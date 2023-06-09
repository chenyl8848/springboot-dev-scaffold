package com.cyl.scaffold.core.exception;

import com.cyl.scaffold.core.constant.ResultCodeEnum;
import com.cyl.scaffold.core.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

import static com.cyl.scaffold.core.constant.ResultCodeEnum.VALID_PARAMS_ERROR;

/**
 * @author cyl
 * @date 2023-03-28 17:14
 * @description 全局异常处理类
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public Result handleException(Exception exception) {
        exception.printStackTrace();
        logger.error(exception.getMessage());
        return Result.fail(exception.getMessage());
    }

    @ExceptionHandler(value = BusinessException.class)
    public Result handleBusinessException(BusinessException exception) {
        exception.printStackTrace();
        logger.error(exception.getMessage());
        return Result.fail(exception.getCode(), exception.getMessage());
    }

    @ExceptionHandler(value = GlobalException.class)
    public Result handleGlobalException(GlobalException exception) {
        exception.printStackTrace();
        logger.error(exception.getMessage());
        return Result.fail(exception.getCode(), exception.getMessage(), null);
    }

    /**
     * 表单绑定到 java bean 出错时，会抛出 BindException 异常
     * 将请求体解析并绑定到 java bean 时，如果出错，则抛出 MethodArgumentNotValidException 异常
     * 普通参数(非 java bean)校验出错时，会抛出 ConstraintViolationException 异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    public Result handleBindException(BindException exception) {
        exception.printStackTrace();
        logger.error(exception.getMessage());
        StringBuffer message = new StringBuffer();
        exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .ifPresent((item) -> message.append(item.getDefaultMessage()));
        return Result.fail(VALID_PARAMS_ERROR.getCode(), message.toString());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        exception.printStackTrace();
        logger.error(exception.getMessage());
        StringBuffer message = new StringBuffer();
        exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .ifPresent((item) -> message.append(item.getDefaultMessage()));
        return Result.fail(ResultCodeEnum.VALID_PARAMS_ERROR.getCode(), message.toString());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public Result handleConstraintViolationException(ConstraintViolationException exception) {
        exception.printStackTrace();
        logger.error(exception.getMessage());
        StringBuffer message = new StringBuffer();
        exception.getConstraintViolations()
                .stream()
                .findFirst()
                .ifPresent((item) -> message.append(item.getMessage()));
        return Result.fail(VALID_PARAMS_ERROR.getCode(), message.toString());
    }
}
