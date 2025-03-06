package com.bladestepapp.lifexpactivityservicemain;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication(scanBasePackages = {"com.bladestepapp.lifexpactivityservicemain", "com.bladestepapp.lifexpactivityserviceapi"})
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

