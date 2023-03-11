package com.rish.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Power {

    /**
     * 角色类型  0用户 1 管理员 2 主管 3 员工
     */
    private Integer roleType;

    /**
     * 角色拥有的权利
     */
    private List<String> rolePower;

}
