package com.codechen.scaffold.framework.config;

import com.codechen.scaffold.common.constant.CommonConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author：Java陈序员
 * @date：2024-11-20 15:27
 * @description：权限配置类
 */
@Configuration
@ConfigurationProperties(prefix = "codechen.auth")
@Data
public class AuthConfig {

    /** Token 密钥 */
    private String tokenSecret;

    /** Token 过期时间 */
    private Long tokenExpiredTime;

    /** 白名单 */
    private String excludeUrls;
}
