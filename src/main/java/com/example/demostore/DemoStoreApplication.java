package com.example.demostore;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.demostore.mapper")
public class DemoStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoStoreApplication.class, args);
    }

}
