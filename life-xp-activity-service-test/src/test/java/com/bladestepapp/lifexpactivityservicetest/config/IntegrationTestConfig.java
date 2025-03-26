package com.bladestepapp.lifexpactivityservicetest.config;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.List;

@Configuration
@EnableFeignClients(basePackages = "com.bladestepapp.api")
@ComponentScan(basePackages = "com.bladestepapp.lifexpactivityserviceinfrastructure.gateway")
@ImportAutoConfiguration(FeignAutoConfiguration.class)
public class IntegrationTestConfig {

    @Bean
    public HttpMessageConverters httpMessageConverters() {
        return new HttpMessageConverters(false, List.of(new MappingJackson2HttpMessageConverter()));
    }
}