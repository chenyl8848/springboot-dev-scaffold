package com.cyl.scaffold.core.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.cyl.scaffold.core.constant.CommonCodeEnum;
import com.cyl.scaffold.core.constant.ResultCodeEnum;
import com.cyl.scaffold.core.entity.Result;
import com.cyl.scaffold.core.util.JwtUtil;
import com.cyl.scaffold.core.util.ThreadLocalUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author cyl
 * @date 2023-05-10 16:25
 * @description jwt 拦截器
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Value("${platform.config.auth.tokenKey}")
    private String tokenKey;

    @Value("${platform.config.auth.tokenSignSecret}")
    private String tokenSignSecret;

    @Value("${platform.config.auth.excludeUrls}")
    private List<String> excludeUrls;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        //获取请求地址
        String url = request.getRequestURI();

        Result<Object> result = Result.build(null, null, null);

        //先验证是否有白名单放行
        if (!isExcludeUrls(url)) {
            if (true) {
                // 从请求头中获取token
                String token = request.getHeader(tokenKey);

                if (StringUtils.isNotBlank(token)) {
                    //  验证token是否有效
                    try {
                        Map<String, Object> userInfo = JwtUtil.validate(token, tokenSignSecret);
                        ThreadLocalUtil.set(CommonCodeEnum.THREAD_LOCAL_LOGIN_USER_KEY.getCode(), userInfo.get("username"));
                        return true;
                    } catch (TokenExpiredException e) {
                        e.printStackTrace();
                        result = Result.fail(ResultCodeEnum.NO_ACCESS_PRIVILEGES.getCode(), tokenKey + "已经过期");
                    } catch (SignatureVerificationException e) {
                        e.printStackTrace();
                        result = Result.fail(ResultCodeEnum.NO_ACCESS_PRIVILEGES.getCode(), tokenKey + "签名错误");
                    } catch (AlgorithmMismatchException e) {
                        e.printStackTrace();
                        result = Result.fail(ResultCodeEnum.NO_ACCESS_PRIVILEGES.getCode(), tokenKey + "加密算法不匹配");
                    } catch (Exception e) {
                        e.printStackTrace();
                        result = Result.fail(ResultCodeEnum.NO_ACCESS_PRIVILEGES.getCode(), tokenKey + "无效");
                    }
                } else {
                    result = Result.fail(ResultCodeEnum.NO_ACCESS_PRIVILEGES.getCode(), tokenKey + "不能为空");
                }

                String json = new ObjectMapper().writeValueAsString(result);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().println(json);
                return false;
            }
        }

        return true;
    }

    private boolean isExcludeUrls(String uri) {
        boolean isSkip = false;
        if (excludeUrls == null || excludeUrls.size() < 0) {
            isSkip = false;
        }

        for (String url : excludeUrls) {
            if (StringUtils.isBlank(url)) {
                throw new IllegalArgumentException("白名单不能为空");
            }

            if (!url.equals("/**") && !url.equals("**")) {
                if (url.endsWith("/**")) {
                    if (uri.contains(url.substring(0, url.length() - 3))) {
                        isSkip = true;
                        break;
                    }
                } else {
                    if (url.equals(uri)) {
                        isSkip = true;
                        break;
                    }
                }
            } else {
                isSkip = true;
                break;
            }
        }

        return isSkip;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }
}
