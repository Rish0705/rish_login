package com.rish.domain.req;

import lombok.Data;

@Data
public class LoginReq {
    /**
     * 登录名
     */
    private String loginName;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 验证码
     */
    private String code;
}
