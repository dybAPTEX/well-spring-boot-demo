package com.well.studio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhaojie
 * @date 2020/07/14 11:05:44
 */
@SpringBootApplication(scanBasePackages = "com.well.studio")
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class,args);
    }
}