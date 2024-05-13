package com.codechen.scaffold.core.filter;

import com.codechen.scaffold.core.constant.CommonCodeEnum;
import com.codechen.scaffold.core.util.SnowflakeIdUtil;
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
 * @author cyl
 * @date 2023-03-31 8:51
 * @description 全局日志过滤器
 */
public class LogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String traceId = request.getHeader(CommonCodeEnum.TRACE_ID.getCode());
        if (StringUtils.isBlank(traceId)) {
            SnowflakeIdUtil snowflakeIdUtil = new SnowflakeIdUtil(0, 0);
            traceId = snowflakeIdUtil.nextId() + "";
        }
        MDC.put(CommonCodeEnum.TRACE_ID.getCode(), traceId);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        MDC.remove(CommonCodeEnum.TRACE_ID.getCode());
    }
}
