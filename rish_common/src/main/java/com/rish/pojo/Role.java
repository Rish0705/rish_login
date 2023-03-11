package com.rish.pojo;

import lombok.Data;


@Data
public class Role {

    /**
     * 角色类型  0用户 1 管理员 2 主管 3 员工
     */
    private Integer roleType;

    /**
     * 角色民称
     */
    private String roleName;

    /**
     * 是否开启该角色
     */
    private Integer status;

}
