package com.bladestepapp.lifexpactivityservicemain;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Arrays;

@SpringBootApplication(scanBasePackages = {"com.bladestepapp.lifexpactivityservicemain", "com.bladestepapp.lifexpactivityserviceapi",
        "com.bladestepapp.lifexpactivityserviceapplication","com.bladestepapp.lifexpactivityserviceinfrastructure"
})
@EnableMongoRepositories(basePackages = "com.bladestepapp.lifexpactivityserviceinfrastructure.repositories")
@EntityScan(basePackages = "com.bladestepapp.lifexpactivityserviceinfrastructure.entities")
public class LifeXpActivityServiceMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(LifeXpActivityServiceMainApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println("Loaded beans:");
            Arrays.stream(ctx.getBeanDefinitionNames()).sorted().forEach(System.out::println);
        };
    }
}

