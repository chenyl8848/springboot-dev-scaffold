package com.codechen.scaffold.framework.filter;

import com.alibaba.fastjson2.JSON;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.codechen.scaffold.common.constant.CommonConstant;
import com.codechen.scaffold.common.domain.Result;
import com.codechen.scaffold.common.domain.UserInfo;
import com.codechen.scaffold.common.enums.ResultCodeEnum;
import com.codechen.scaffold.common.holder.UserInfoHolder;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author：Java陈序员
 * @date：2024-10-29 15:57
 * @description：Token 过滤器
 */
public class TokenFilter implements Filter {

    /** 请求白名单 */
    private List<String> excludeURIs = new ArrayList<>();

    /** Token 密钥 */
    private String tokenSecret = "";

    @Override
    public void init(FilterConfig filterConfig) {
        String excludeURIs = filterConfig.getInitParameter(CommonConstant.EXCLUDE_URIS_KEY);
        if (StringUtils.isNotBlank(excludeURIs)) {
            this.excludeURIs.addAll(Arrays.asList(excludeURIs.split(",")));
        }

        String tokenSecret = filterConfig.getInitParameter(CommonConstant.TOKEN_SECRET);
        if (StringUtils.isNotBlank(tokenSecret)) {
            this.tokenSecret = tokenSecret;
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 获取请求地址
        String requestURI = request.getServletPath();
        if (isExcludeURI(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 获取 Token
        String token = request.getHeader(CommonConstant.TOKEN_KEY);
        if (StringUtils.isBlank(token)) {
            writeResponse(response, Result.fail(ResultCodeEnum.UN_AUTHORIZED.getCode(), CommonConstant.TOKEN_KEY + " 不能为空"));
            return;
        }

        try {
            UserInfo userInfo = UserInfoHolder.decodeToken(token, tokenSecret);
            UserInfoHolder.setUserInfo(userInfo);
            filterChain.doFilter(request, response);
        } catch (TokenExpiredException e) {
            e.printStackTrace();
            writeResponse(response, Result.fail(ResultCodeEnum.UN_AUTHORIZED.getCode(), CommonConstant.TOKEN_KEY + " 已经过期"));
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
            writeResponse(response, Result.fail(ResultCodeEnum.UN_AUTHORIZED.getCode(), CommonConstant.TOKEN_KEY + " 签名错误"));
        } catch (AlgorithmMismatchException e) {
            e.printStackTrace();
            writeResponse(response, Result.fail(ResultCodeEnum.UN_AUTHORIZED.getCode(), CommonConstant.TOKEN_KEY + " 加密算法不匹配"));
        } catch (Exception e) {
            e.printStackTrace();
            writeResponse(response, Result.fail(ResultCodeEnum.UN_AUTHORIZED.getCode(), CommonConstant.TOKEN_KEY + " 无效"));
        } finally {
            UserInfoHolder.removeUserInfo();
        }
    }

    private boolean isExcludeURI(String requestURI) {
        if (this.excludeURIs.size() == 0) {
            return false;
        }

        for (String uri : this.excludeURIs) {
            requestURI = requestURI.trim();
            uri = uri.trim();

            if (requestURI.toLowerCase().matches(uri.toLowerCase().replace("*", ".*"))) {
                return true;
            }
        }
        return false;
    }

    private void writeResponse(HttpServletResponse response, Result result) {
        String json = JSON.toJSONString(result);
        response.setContentType("application/json;charset=UTF-8");
        try {
            response.getWriter().println(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
