package com.codechen.scaffold.common.constant;

/**
 * @author：Java陈序员
 * @date：2024-10-29 17:06
 * @description：通用枚举类
 */
public interface CommonConstant {

    /** Token Key */
    String TOKEN_KEY = "ApiToken";

    /** Token Secret Key */
    String TOKEN_SECRET = "tokenSecret";

    /** 白名单 Key */
    String EXCLUDE_URIS_KEY = "excludeURIs";

    /** ThreadLocal User Key */
    String THREAD_LOCAL_LOGIN_USER_KEY = "user";

    /** 日志追踪 KEY */
    String TRACE_ID_KEY = "TRACE_ID";

//    THREAD_LOCAL_LOGIN_USER_KEY("user", "登录用户key");

    /** 菜单 */
    Integer MENU = 1;

    /** 按钮 */
    Integer BUTTON = 2;

    /** 管理员用户名 */
    String ADMIN_USER_NAME = "admin";

    /** JSON 请求格式 */
    String APPLICATION_JSON = "application/json";
}
