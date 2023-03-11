package com.rish.service;


import com.rish.domain.req.LoginReq;
import com.rish.domain.res.BaseRes;
import com.rish.domain.res.LoginRes;

public interface LoginService {


    String sayHello(String name);

    BaseRes<LoginRes> login(LoginReq req);

    BaseRes<String> sendCode(String phoneNumber);

}
