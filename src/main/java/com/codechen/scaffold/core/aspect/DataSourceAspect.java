package com.codechen.scaffold.core.aspect;

import com.codechen.scaffold.core.annotation.DataSourceType;
import com.codechen.scaffold.core.holder.DataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author cyl
 * @date 2023-04-28 11:15
 * @description 多数据源切面类
 */
@Aspect
@Component
@Order(1)
public class DataSourceAspect {

    @Pointcut("@annotation(com.codechen.scaffold.core.annotation.DataSourceType) || @within(com.codechen.scaffold.core.annotation.DataSourceType)")
    public void pointCut() {

    }

    @Around(value = "pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        DataSourceType dataSourceType = getDataSource(joinPoint);

        if (Objects.nonNull(dataSourceType)) {
            DataSourceContextHolder.setDataSourceType(dataSourceType.value().name());
        }

        try {
            return joinPoint.proceed();
        } finally {
            // 在方法执行之后，清空数据源类型缓存
            DataSourceContextHolder.clear();
        }
    }

    /**
     * 获取数据源类型
     * 1.先从方法上判断是否有 @DataSourceType 注解
     * 2.如果方法上没有 @DataSourceType 注解，再从类类上获取
     *
     * @param joinPoint
     * @return
     */
    public DataSourceType getDataSource(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        DataSourceType dataSourceType = AnnotationUtils.getAnnotation(methodSignature.getMethod(), DataSourceType.class);
        if (Objects.nonNull(dataSourceType)) {
            return dataSourceType;
        }

        return AnnotationUtils.getAnnotation(joinPoint.getSignature().getDeclaringType(), DataSourceType.class);
    }
}
