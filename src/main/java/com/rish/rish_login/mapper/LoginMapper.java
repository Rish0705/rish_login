package com.rish.rish_login.mapper;


import com.rish.rish_login.pojo.Login;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginMapper {
    /**
     * 用户名密码登录判断
     */
    Login loginName(@Param("login_name")String loginName, @Param("password") String password);

    /**
     * 手机号登录
     */
    Login loginPhone(String phoneNumber);
}
