package com.javaeduapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class JavaEduAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaEduAppApplication.class, args);
    }
}
