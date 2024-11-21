package com.codechen.scaffold.framework.filter;

import com.codechen.scaffold.common.constant.CommonConstant;
import com.codechen.scaffold.common.util.SnowflakeIdUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author：Java陈序员
 * @date 2023-03-31 8:51
 * @description 全局日志过滤器
 */
public class LogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String traceId = request.getHeader(CommonConstant.TRACE_ID_KEY);
        if (StringUtils.isBlank(traceId)) {
            SnowflakeIdUtil snowflakeIdUtil = new SnowflakeIdUtil(0, 0);
            traceId = snowflakeIdUtil.nextId() + "";
        }
        MDC.put(CommonConstant.TRACE_ID_KEY, traceId);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        MDC.remove(CommonConstant.TRACE_ID_KEY);
    }
}
