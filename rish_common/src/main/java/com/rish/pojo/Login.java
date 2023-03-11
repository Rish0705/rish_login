package com.rish.pojo;

import lombok.Data;

@Data
public class Login {
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
     * 角色类型  0用户 1 管理员 2 主管 3 员工
     */
    private Integer roleType;
}
