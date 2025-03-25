package com.bladestepapp.lifexpactivityservicetest.integration.support;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.support.TestPropertySourceUtils;

public class WireMockInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final WireMockServer wireMockServer = new WireMockServer(wireMockConfig().dynamicPort());
    private static final int WIREMOCK_PORT;

    static {
        wireMockServer.start();
        WIREMOCK_PORT = wireMockServer.port();
        WireMock.configureFor("localhost", WIREMOCK_PORT);
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                applicationContext,
                "user.url=http://localhost:" + WIREMOCK_PORT
        );
    }
}