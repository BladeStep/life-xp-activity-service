package com.bladestepapp.lifexpactivityservicemain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = {"com.bladestepapp.lifexpactivityservicemain", "com.bladestepapp.lifexpactivityserviceapi",
        "com.bladestepapp.lifexpactivityserviceapplication","com.bladestepapp.lifexpactivityserviceinfrastructure"
})
@EnableMongoRepositories(basePackages = "com.bladestepapp.lifexpactivityserviceinfrastructure.repository")
@EntityScan(basePackages = "com.bladestepapp.lifexpactivityserviceinfrastructure.entities")
public class LifeXpActivityServiceMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(LifeXpActivityServiceMainApplication.class, args);
    }
}

