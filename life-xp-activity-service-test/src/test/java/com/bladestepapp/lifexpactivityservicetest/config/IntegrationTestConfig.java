package com.bladestepapp.lifexpactivityservicetest.config;

import com.bladestepapp.lifexpactivityserviceinfrastructure.properties.UserServiceProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@ComponentScan(basePackages = "com.bladestepapp.lifexpactivityserviceinfrastructure.gateway")
public class IntegrationTestConfig {

    @Bean
    public WebClient userWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:" + WireMockTestConfig.wireMockContainer.getMappedPort(8080))
                .build();
    }

    @Bean
    public UserServiceProperties userServiceProperties() {
        UserServiceProperties properties = new UserServiceProperties();
        properties.setBaseUrl("http://localhost:" + WireMockTestConfig.wireMockContainer.getMappedPort(8080));
        properties.setUserPath("/api/user/{id}");
        return properties;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
}