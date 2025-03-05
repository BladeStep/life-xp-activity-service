package com.bladestepapp.lifexpxpservicemain;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication(scanBasePackages = {"com.bladestepapp.lifexpxpservicemain"})
public class LifeXpXpServiceMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(LifeXpXpServiceMainApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println("Loaded beans:");
            Arrays.stream(ctx.getBeanDefinitionNames()).sorted().forEach(System.out::println);
        };
    }
}

