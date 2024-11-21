package com.codechen.scaffold.common.util;

import com.alibaba.fastjson2.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Objects;

/**
 * @author：Java陈序员
 * @date 2023-05-10 11:27
 * @description jwt token 工具类
 */
public class JWTUtil {

    private static final String CLAIM_INFO_KEY = "userInfo";

    /**
     * 生成 token
     *
     * @param userName   用户名
     * @param userInfo   用户信息
     * @param secret     密钥
     * @param expireTime 过期时间
     * @return
     */
    public static String generateToken(String userName, Object userInfo, String secret, long expireTime) {

        long currentTimeMillis = System.currentTimeMillis();
        if (Objects.nonNull(expireTime) && expireTime > 0l) {
            expireTime += currentTimeMillis;
        } else {
            expireTime = currentTimeMillis + 30 * 60 * 1000;
        }

        return JWT.create()
                .withIssuer(userName)
                .withExpiresAt(new Date(currentTimeMillis))
                .withClaim(CLAIM_INFO_KEY, JSON.toJSONString(userInfo))
                .withExpiresAt(new Date(expireTime))
                .sign(Algorithm.HMAC256(secret));
    }

    /**
     * 解析 token
     *
     * @param token  token
     * @param secret 密钥
     * @return 用户信息
     */
    public static String decodeToken(String token, String secret) {
        if (StringUtils.isEmpty(token)) {
            throw new IllegalArgumentException("token 不能为空");
        }

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
        DecodedJWT verify = jwtVerifier.verify(token);
        String userInfo = verify.getClaim(CLAIM_INFO_KEY).asString();
        return userInfo;
    }

}
