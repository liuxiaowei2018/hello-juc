package com.open;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liuxiaowei
 * @date 2022年09月26日 10:37
 * @Description
 */
@SpringBootApplication
public class HelloConcurrentApplication {
    public static void main(String[] args) {
        SpringApplication.run(HelloConcurrentApplication.class, args);
    }
}
