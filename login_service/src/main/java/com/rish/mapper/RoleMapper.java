package com.rish.mapper;


import com.rish.pojo.Role;
import com.rish.pojo.RolePower;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface RoleMapper {

    /**
     * 获取角色
     */
    Role getRole(Integer roleType);

    List<RolePower> getAll(Integer roleType);
}
