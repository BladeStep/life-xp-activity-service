package com.bladestepapp.lifexpactivityservicetest.e2e.annotation;

import com.bladestepapp.lifexpactivityservicetest.config.IntegrationTestConfig;
import com.bladestepapp.lifexpactivityservicetest.config.WireMockTestConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = {IntegrationTestConfig.class})
@ContextConfiguration(initializers = WireMockTestConfig.class)
@Import({WireMockTestConfig.class})
@Testcontainers
public @interface IntegrationTest {
}
