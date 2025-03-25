package com.bladestepapp.lifexpactivityservicetest.e2e.config;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.GenericContainer;

@TestConfiguration
public class WireMockContainerConfig implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final GenericContainer<?> wireMockContainer =
            new GenericContainer<>("wiremock/wiremock:3.3.1")
                    .withExposedPorts(8080);

    static {
        wireMockContainer.start();
    }

    @Override
    public void initialize(ConfigurableApplicationContext context) {
        TestPropertyValues.of(
                "user.url=http://localhost:" + wireMockContainer.getMappedPort(8080)
        ).applyTo(context);
    }

    @Bean
    public WireMock wireMockClient() {
        return WireMock.create()
                .host("localhost")
                .port(wireMockContainer.getMappedPort(8080))
                .build();
    }
}