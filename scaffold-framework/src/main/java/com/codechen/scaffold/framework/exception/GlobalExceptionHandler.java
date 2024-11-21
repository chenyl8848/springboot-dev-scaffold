package com.codechen.scaffold.framework.exception;

import com.codechen.scaffold.common.domain.Result;
import com.codechen.scaffold.common.enums.ResultCodeEnum;
import com.codechen.scaffold.common.exception.BusinessException;
import com.codechen.scaffold.common.exception.GlobalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

/**
 * @author：Java陈序员
 * @date 2023-03-28 17:14
 * @description 全局异常处理类
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public Result handleException(Exception exception, HttpServletRequest request) {
        logger.error("请求地址【{}】==>请求方式【{}】", request.getRequestURI(), request.getMethod());
        logger.error(exception.getMessage());
        exception.printStackTrace();

        return Result.fail(ResultCodeEnum.FAIL.getCode(), exception.getMessage());
    }

    @ExceptionHandler(value = BusinessException.class)
    public Result handleBusinessException(BusinessException exception, HttpServletRequest request) {
        logger.error("请求地址【{}】==>请求方式【{}】", request.getRequestURI(), request.getMethod());
        logger.error(exception.getMessage());
        exception.printStackTrace();

        return Result.fail(exception.getCode(), exception.getMessage());
    }

    @ExceptionHandler(value = GlobalException.class)
    public Result handleGlobalException(GlobalException exception, HttpServletRequest request) {
        logger.error("请求地址【{}】==>请求方式【{}】", request.getRequestURI(), request.getMethod());
        logger.error(exception.getMessage());
        exception.printStackTrace();

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
    public Result handleBindException(BindException exception, HttpServletRequest request) {
        logger.error("请求地址【{}】==>请求方式【{}】", request.getRequestURI(), request.getMethod());
        logger.error(exception.getMessage());
        exception.printStackTrace();

        StringBuffer message = new StringBuffer();
        exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .ifPresent((item) -> message.append(item.getDefaultMessage()));
        return Result.fail(ResultCodeEnum.PARAM_ABSENT.getCode(), message.toString());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest request) {
        logger.error("请求地址【{}】==>请求方式【{}】", request.getRequestURI(), request.getMethod());
        logger.error(exception.getMessage());
        exception.printStackTrace();

        StringBuffer message = new StringBuffer();
        exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .ifPresent((item) -> message.append(item.getDefaultMessage()));
        return Result.fail(ResultCodeEnum.PARAM_ABSENT.getCode(), message.toString());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public Result handleConstraintViolationException(ConstraintViolationException exception, HttpServletRequest request) {
        logger.error("请求地址【{}】==>请求方式【{}】", request.getRequestURI(), request.getMethod());
        exception.printStackTrace();
        logger.error(exception.getMessage());

        StringBuffer message = new StringBuffer();
        exception.getConstraintViolations()
                .stream()
                .findFirst()
                .ifPresent((item) -> message.append(item.getMessage()));
        return Result.fail(ResultCodeEnum.PARAM_ABSENT.getCode(), message.toString());
    }
}
