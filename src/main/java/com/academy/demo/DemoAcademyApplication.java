package com.academy.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication // @Configuration & @ComponentScan | SpringBootServletInitializer
public class DemoAcademyApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoAcademyApplication.class, args);
    }

}
