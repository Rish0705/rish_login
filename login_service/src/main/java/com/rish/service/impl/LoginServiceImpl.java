package com.rish.service.impl;


import com.rish.domain.req.LoginReq;
import com.rish.domain.res.BaseRes;
import com.rish.domain.res.LoginRes;
import com.rish.mapper.LoginMapper;
import com.rish.pojo.Login;
import com.rish.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Slf4j
@Service
@DubboService
public class LoginServiceImpl implements LoginService {
    private final LoginMapper loginMapper;

    private final RedisTemplate<String, String> redisTemplate;

    public LoginServiceImpl(LoginMapper loginMapper, RedisTemplate<String, String> redisTemplate) {
        this.loginMapper = loginMapper;
        this.redisTemplate = redisTemplate;
    }


    public String sayHello(String name) {
        return "Hello" + name;
    }

    public BaseRes<LoginRes> login(LoginReq req) {
        log.info("用户开始登录");
        String loginName = req.getLoginName();
        String password = req.getPassword();
        String phoneNumber = req.getPhoneNumber();
        String reqCode = req.getCode();

        if (loginName != null && password != null) {
            // 账号密码登录,去查数据库
            Login login = loginMapper.loginName(loginName, password);
            if (login == null) {
                // 没查到这条数据，说明用户名或密码不对
                return BaseRes.error("账户名或密码错误");
            } else {
                // 登录成功
                log.info("用户通过用户名密码登录，登录成功");
                String token = UUID.randomUUID().toString();
                token = "rish.nameLogin." + token;
                LoginRes loginRes = new LoginRes();
                loginRes.setToken(token);
                // 将token存入内存正常value应该为userID或者是其他的信息
                redisTemplate.opsForValue().set(token, loginName, 2, TimeUnit.DAYS);
                return BaseRes.success(loginRes);
            }
        }
        if (reqCode != null && phoneNumber != null) {
            Login login = loginMapper.loginPhone(phoneNumber);
            if (!phoneNumber.equals(login.getPhoneNumber()))
                return BaseRes.error("手机号或验证码不正确");
            // 使用手机号方式登录
            String sbCode = redisTemplate.opsForValue().get(phoneNumber);
            if (reqCode.equals(sbCode)) {
                // 验证码一样，登录成功
                log.info("用户通过手机号验证码登录，登录成功");
                String token = UUID.randomUUID().toString();
                token = "rish.phoneNumberLogin." + token;
                LoginRes loginRes = new LoginRes();
                loginRes.setToken(token);
                // 将token存入内存正常value应该为userID或者是其他的信息
                redisTemplate.opsForValue().set(token, phoneNumber, 2, TimeUnit.DAYS);
                return BaseRes.success(loginRes);
            } else {
                // 验证码不一致
                return BaseRes.error("验证码不正确");
            }
        }
        // 什么数据都没有传进去
        return BaseRes.error("数据异常");
    }

    public BaseRes<String> sendCode(String phoneNumber) {
        // 查询用户表中是否存在该手机号
        Login loginPhone = loginMapper.loginPhone(phoneNumber);

        if (loginPhone == null) return BaseRes.error("请输入手机号");  // 如果手机号不存在，直接返回
        if (redisTemplate.opsForValue().get(phoneNumber) != null) return BaseRes.error("验证码已发送");  // 避免5分钟内重复发送

        // 生成5位短信验证码
        StringBuilder sbCode = new StringBuilder();
        Stream
                .generate(() -> new Random().nextInt(10))
                .limit(5)
                .forEach(sbCode::append);
        // 将验证码放入redis  ，5分钟过期 expire
        redisTemplate.opsForValue().set(phoneNumber, sbCode.toString(), Duration.ofMinutes(5));
        log.info("短信验证码：" + sbCode);
        return BaseRes.success("验证码已生成");
    }
}
