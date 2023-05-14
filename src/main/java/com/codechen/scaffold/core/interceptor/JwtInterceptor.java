package com.codechen.scaffold.core.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.codechen.scaffold.core.entity.Result;
import com.codechen.scaffold.core.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author cyl
 * @date 2023-05-10 16:25
 * @description jwt 拦截器
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        //获取请求地址
        String url = request.getRequestURL().toString();

        Result<Object> result = new Result<>();

        //先验证是否有白名单放行
        // if (!isExcludeUrls(url)) {
        if (true) {
            // 从请求头中获取token
            String token = request.getHeader("token");

            //  验证token是否有效
            try {
                JwtUtil.validate(token, "");
                return true;
            } catch (TokenExpiredException e) {
                e.printStackTrace();
                result = Result.fail("token已经过期");
            } catch (SignatureVerificationException e) {
                e.printStackTrace();
                result = Result.fail("签名错误");
            } catch (AlgorithmMismatchException e) {
                e.printStackTrace();
                result = Result.fail("加密算法不匹配");
            } catch (Exception e) {
                e.printStackTrace();
                result = Result.fail("无效token");
            }

            String json = new ObjectMapper().writeValueAsString(result);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(json);
            return false;
        }

        return true;
    }

}
