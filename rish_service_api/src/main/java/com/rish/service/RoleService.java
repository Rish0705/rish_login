package com.rish.service;



import com.rish.domain.res.BaseRes;
import com.rish.pojo.Role;

import java.util.List;

public interface RoleService {

    BaseRes<Role> getRole(Integer roleType);

    List<String> findList(Role role);
}
