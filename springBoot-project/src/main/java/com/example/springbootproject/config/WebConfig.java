package com.example.springbootproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 配置哪些路径支持跨域请求
                .allowedOriginPatterns("*")  // 允许的前端源地址
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 允许的请求方法
                .allowedHeaders("*")  // 允许所有的请求头
                .allowCredentials(true)// 是否允许发送Cookie
                .maxAge(3600);  //3600s不访问
    }
}
