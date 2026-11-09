package com.classcraft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ClassCraftApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClassCraftApplication.class, args);
    }
}
