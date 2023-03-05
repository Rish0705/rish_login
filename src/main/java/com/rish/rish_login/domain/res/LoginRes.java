package com.rish.rish_login.domain.res;

import lombok.Data;

@Data
public class LoginRes {
    /**
     * 鉴权令牌
     */
    private String token;

    /**
     * 用户类型  0用户 1 管理员 2 主管 3 员工
     */
    private Integer loginType;
}
