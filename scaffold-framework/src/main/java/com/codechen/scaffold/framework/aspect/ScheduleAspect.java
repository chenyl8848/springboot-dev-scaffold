package com.codechen.scaffold.framework.aspect;

import com.codechen.scaffold.common.constant.CommonConstant;
import com.codechen.scaffold.common.util.ThreadMdcUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

/**
 * @author：Java陈序员
 * @date：2025-3-11 14:58
 * @description：定时任务注解切面类
 */
@Aspect
@Component
public class ScheduleAspect {

    @Pointcut("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public void pointCut() {

    }

    @Around("pointCut()")
    public void doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            MDC.put(CommonConstant.TRACE_ID_KEY, ThreadMdcUtil.generateTraceId());
            joinPoint.proceed();
        } finally {
            MDC.remove(CommonConstant.TRACE_ID_KEY);
        }
    }
}
