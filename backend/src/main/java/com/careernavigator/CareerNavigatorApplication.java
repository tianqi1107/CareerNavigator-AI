package com.careernavigator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.careernavigator.mapper")
@EnableCaching
public class CareerNavigatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CareerNavigatorApplication.class, args);
    }
}
