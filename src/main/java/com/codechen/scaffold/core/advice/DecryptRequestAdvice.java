package com.codechen.scaffold.core.advice;

import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.codechen.scaffold.core.annotation.SecretParam;
import com.codechen.scaffold.core.util.RSAUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * @author cyl
 * @date 2023-04-28 9:54
 * @description 请求参数解密
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DecryptRequestAdvice extends RequestBodyAdviceAdapter {

    private static final String REQUEST_DATA_KEY = "data";

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return methodParameter.hasMethodAnnotation(SecretParam.class);
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        if (parameter.hasMethodAnnotation(SecretParam.class)) {
            SecretParam secretParam = parameter.getMethodAnnotation(SecretParam.class);
            if (secretParam.decrypt()) {
                return new HttpInputMessage() {
                    @Override
                    public InputStream getBody() throws IOException {

                        InputStream inputMessageBody = inputMessage.getBody();
                        String requestBodyString = IoUtil.read(inputMessageBody, StandardCharsets.UTF_8);
                        JSONObject requestBodyJson = JSON.parseObject(requestBodyString);
                        String requestData = requestBodyJson.getString(REQUEST_DATA_KEY);
                        if (StringUtils.isBlank(requestData)) {
                            throw new IllegalArgumentException("请求参数不能为空");
                        }

                        String decryptRequestData = RSAUtil.decrypt(requestData, "");

                        return IoUtil.toStream(decryptRequestData, StandardCharsets.UTF_8);
                    }

                    @Override
                    public HttpHeaders getHeaders() {
                        return inputMessage.getHeaders();
                    }
                };
            }
        }
        return inputMessage;
    }
}
