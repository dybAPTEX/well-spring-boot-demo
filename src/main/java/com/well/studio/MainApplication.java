package com.well.studio;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author zhaojie
 * @date 2020/07/14 11:05:44
 */
@MapperScan("com.well.studio.mapper")
@SpringBootApplication(scanBasePackages = "com.well.studio")
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class,args);
    }
}