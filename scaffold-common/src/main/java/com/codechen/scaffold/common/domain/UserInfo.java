package com.codechen.scaffold.common.domain;

/**
 * @author：Java陈序员
 * @date：2024-10-29 17:26
 * @description：登录用户信息
 */
public interface UserInfo {

    /** 用户 ID */
    Long getUserId();

    /** 用户名 */
    String getUsername();

    /** 用户昵称 */
    String getUserNickName();

}
