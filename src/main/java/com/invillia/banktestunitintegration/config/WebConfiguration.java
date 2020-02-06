package com.invillia.banktestunitintegration.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@EnableWebMvc
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        final String[] methods = {"GET", "POST", "PUT", "DELETE", "OPTIONS"};
        registry
                .addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods(methods)
                .allowedHeaders("*");

    }
}