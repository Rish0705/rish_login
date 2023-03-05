package com.rish.rish_login.controller;

import com.rish.rish_login.domain.req.LoginReq;
import com.rish.rish_login.domain.res.BaseRes;
import com.rish.rish_login.domain.res.LoginRes;
import com.rish.rish_login.service.LoginService;
import org.springframework.web.bind.annotation.*;


@RestController
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * 登录方法
     * @param req LoginReq对象
     * @return token + loginType
     */
    @PostMapping("/login")
    public BaseRes<LoginRes> loginName(@RequestBody LoginReq req) {
        return loginService.login(req);
    }

    /**
     * 传入手机号，获得验证码
     * @param phoneNumber 手机号
     */
    @GetMapping("/code/{phoneNumber}")
    public BaseRes<String> generateCode(@PathVariable String phoneNumber){
        return loginService.sendCode(phoneNumber);
    }

}
