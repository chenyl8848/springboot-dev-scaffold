package com.codechen.scaffold.core.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author：Java陈序员
 * @date 2023-05-10 11:27
 * @description cookie 工具类
 */
public class CookieUtil {

    /**
     * 创建 cookie
     *
     * @param name   key
     * @param value  value
     * @param maxAge 生命周期
     * @return cookie
     */
    public static Cookie createCookie(String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");

        return cookie;
    }

    /**
     * 添加 cookie
     *
     * @param response
     * @param name     key
     * @param value    value
     * @param maxAge   声明周期
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = createCookie(name, value, maxAge);
        response.addCookie(cookie);

    }

    /**
     * 获取所有的 cookie 信息
     *
     * @param request
     * @return
     */
    public static Map<String, String> readCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            return Arrays.stream(cookies).collect(Collectors.toMap(Cookie::getName, Cookie::getValue));
        } else {
            return new HashMap<>();
        }
    }

    /**
     * 获取 cookie 信息
     *
     * @param request
     * @param name
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String name) {
        Map<String, String> cookies = readCookies(request);
        return cookies.get(name);
    }
}
