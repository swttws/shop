package com.su;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication//取消数据源自动配置
@MapperScan("com.su.mapper")
public class WxLoginApplication {
    public static void main(String[] args) {
        SpringApplication.run(WxLoginApplication.class, args);
    }
}
