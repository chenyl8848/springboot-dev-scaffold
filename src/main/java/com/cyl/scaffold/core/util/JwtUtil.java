package com.cyl.scaffold.core.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Map;

/**
 * @author cyl
 * @date 2023-05-10 11:27
 * @description jwt token 工具类
 */
public class JwtUtil {

    public static String generateToken(String userName, Map<String, Object> info, String secret, long expireTime) {

        long currentTimeMillis = System.currentTimeMillis();
        if (expireTime > 0l) {
            expireTime += currentTimeMillis;
        }
        expireTime = currentTimeMillis + 30 * 60 * 1000;

        return JWT.create()
                .withIssuer(userName)
                .withExpiresAt(new Date(currentTimeMillis))
                .withClaim("info", info)
                .withExpiresAt(new Date(expireTime))
                .sign(Algorithm.HMAC256(secret));

    }

    public static void validate(String token, String secret) {
        if (StringUtils.isEmpty(token)) {
            throw new IllegalArgumentException("token 不能为空");
        }

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
        jwtVerifier.verify(token);

    }

}
