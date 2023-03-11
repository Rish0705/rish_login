package com.rish.controller;


import com.rish.service.impl.RoleServiceImpl;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {
    private final RoleServiceImpl roleService;

    public RoleController(RoleServiceImpl roleService) {
        this.roleService = roleService;
    }
}
