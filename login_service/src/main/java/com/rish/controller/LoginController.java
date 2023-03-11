package com.rish.controller;


import com.rish.domain.req.LoginReq;
import com.rish.domain.res.BaseRes;
import com.rish.domain.res.LoginRes;
import com.rish.domain.res.TokenRes;
import com.rish.service.impl.LoginServiceImpl;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/login")
public class LoginController {
    private final LoginServiceImpl loginService;

    private final StringRedisTemplate template;

    public LoginController(LoginServiceImpl loginService, StringRedisTemplate template) {
        this.loginService = loginService;
        this.template = template;
    }


    /**
     * 登录方法
     *
     * @param req LoginReq对象
     * @return token + loginType
     */
    @PostMapping
    public BaseRes<LoginRes> loginName(@RequestBody LoginReq req) {
        return loginService.login(req);
    }

    /**
     * 传入手机号，获得验证码
     *
     * @param phoneNumber 手机号
     */
    @GetMapping("/code/{phoneNumber}")
    public BaseRes<String> generateCode(@PathVariable String phoneNumber) {
        return loginService.sendCode(phoneNumber);
    }

    @PostMapping("token")
    public BaseRes<TokenRes> identification(@RequestHeader(value = "token", required = false) String token) {
        if (Boolean.TRUE.equals(template.hasKey(token))) {
            // 确认有token，可以调用服务
            TokenRes tokenRes = new TokenRes();
            tokenRes.setIsState("有效");
            // tokenRes.setRolePower();
            return BaseRes.success(tokenRes);
        }
        return BaseRes.error("鉴权失败");
    }
}
