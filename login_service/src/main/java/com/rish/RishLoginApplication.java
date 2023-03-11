package com.rish;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableDubbo
@MapperScan("com.rish.mapper")
public class RishLoginApplication {

    public static void main(String[] args) {

        SpringApplication.run(RishLoginApplication.class, args);
    }

}
