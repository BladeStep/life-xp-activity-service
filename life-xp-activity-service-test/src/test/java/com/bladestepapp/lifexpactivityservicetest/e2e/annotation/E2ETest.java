package com.bladestepapp.lifexpactivityservicetest.e2e.annotation;

import com.bladestepapp.lifexpactivityservicetest.config.MongoDbTestConfiguration;
import com.bladestepapp.lifexpactivityservicetest.config.TestConfig;
import com.bladestepapp.lifexpactivityservicetest.config.WireMockContainerConfig;
import com.bladestepapp.lifexpactivityservicetest.e2e.extension.MongoCleanupExtension;
import com.bladestepapp.lifexpactivityservicetest.config.KafkaTestConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.TestPropertySource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для E2E тестов.
 * Включает в себя все необходимые настройки для запуска E2E тестов:
 * - Spring Boot конфигурацию с поддержкой REST API
 * - Kafka конфигурацию
 * - MongoDB очистку
 * - Тестовый профиль
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(
        classes = TestConfig.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@ContextConfiguration(
        classes = KafkaTestConfiguration.class,
        initializers = WireMockContainerConfig.class
)
@Import({MongoDbTestConfiguration.class, WireMockContainerConfig.class})
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@ExtendWith(MongoCleanupExtension.class)
public @interface E2ETest {
} 