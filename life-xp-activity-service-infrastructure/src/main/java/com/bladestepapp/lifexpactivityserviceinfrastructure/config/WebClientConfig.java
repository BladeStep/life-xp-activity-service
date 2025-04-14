package com.bladestepapp.lifexpactivityserviceinfrastructure.config;

import com.bladestepapp.lifexpactivityserviceinfrastructure.properties.UserServiceProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    private final UserServiceProperties userServiceProperties;

    @Bean
    public WebClient userWebClient() {

        return WebClient.builder()
                .baseUrl(userServiceProperties.getBaseUrl())
                .build();
    }
}