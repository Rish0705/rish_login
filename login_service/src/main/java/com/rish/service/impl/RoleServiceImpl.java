package com.rish.service.impl;


import com.rish.domain.res.BaseRes;
import com.rish.mapper.RoleMapper;
import com.rish.pojo.Role;
import com.rish.pojo.RolePower;
import com.rish.service.RoleService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@DubboService
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }


    public BaseRes<Role> getRole(Integer roleType){
        Role role = roleMapper.getRole(roleType);
        return BaseRes.success(role);
    }


    public List<String> findList(Role role){
        List<String> rolePower = new ArrayList<>();
        Integer roleType = role.getRoleType();
        List<RolePower> list = roleMapper.getAll(roleType);
        for (RolePower rp: list) {
            rolePower.add(rp.getRolePower().toString());
        }
        return rolePower;
    }
}
