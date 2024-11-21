package com.codechen.scaffold.common.holder;


import com.alibaba.fastjson2.JSON;
import com.codechen.scaffold.common.constant.CommonConstant;
import com.codechen.scaffold.common.domain.UserInfo;
import com.codechen.scaffold.common.util.JWTUtil;
import com.codechen.scaffold.common.util.ThreadLocalUtil;

/**
 * @author：Java陈序员
 * @date：2024-10-29 18:06
 * @description：用户信息接口
 */
public class UserInfoHolder {

    /**
     * 存储用户信息
     *
     * @param userInfo 用户信息
     */
    public static void setUserInfo(UserInfo userInfo) {
        ThreadLocalUtil.set(CommonConstant.THREAD_LOCAL_LOGIN_USER_KEY, userInfo);
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public static UserInfo getUserInfo() {
        Object object = ThreadLocalUtil.get(CommonConstant.THREAD_LOCAL_LOGIN_USER_KEY);
        if (object != null && object instanceof UserInfo) {
            return (UserInfo) object;
        }

        return null;
    }

    /**
     * 移除用户信息
     */
    public static void removeUserInfo() {
        ThreadLocalUtil.remove(CommonConstant.THREAD_LOCAL_LOGIN_USER_KEY);
    }

    /**
     * 解析 Token
     * @param token token
     * @param secret 密钥
     * @return
     */
    public static UserInfo decodeToken(String token, String secret) {
        String decode = JWTUtil.decodeToken(token, secret);
        return JSON.parseObject(decode, UserInfo.class);
    }
}
