package com.bladestepapp.lifexpactivityservicetest.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan(basePackages = {"com.bladestepapp.lifexpactivityserviceapi",
        "com.bladestepapp.lifexpactivityserviceapplication","com.bladestepapp.lifexpactivityserviceinfrastructure"
})
@EnableMongoRepositories(basePackages = "com.bladestepapp.lifexpactivityserviceinfrastructure.persistence")
@EntityScan(basePackages = "com.bladestepapp.lifexpactivityserviceinfrastructure.entities")
@EnableFeignClients(basePackages = "com.bladestepapp.api")
public class TestConfig {
}