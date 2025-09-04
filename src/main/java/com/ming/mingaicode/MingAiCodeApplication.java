package com.ming.mingaicode;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan("com.ming.mingaicode.mapper")
public class MingAiCodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MingAiCodeApplication.class, args);
    }

}
