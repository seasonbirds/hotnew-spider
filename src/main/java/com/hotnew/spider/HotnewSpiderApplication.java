package com.hotnew.spider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HotnewSpiderApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotnewSpiderApplication.class, args);
    }
}
