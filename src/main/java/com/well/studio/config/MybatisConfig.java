package com.well.studio.config;
import com.well.studio.interceptor.MybatisInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: well-spring-boot-demo
 * @description: 配置类
 * @author: daiyunbo
 * @create: 2020-07-30 19:08
 **/

@Configuration
public class MybatisConfig {
    /**
     * 开启mybatis 拦截器
     */
    @Bean
    public Interceptor getInterceptor(){
        return new MybatisInterceptor();
    }
}
