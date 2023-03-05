package com.rish.rish_login;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.rish.rish_login.mapper")
public class RishLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(RishLoginApplication.class, args);
    }

}
