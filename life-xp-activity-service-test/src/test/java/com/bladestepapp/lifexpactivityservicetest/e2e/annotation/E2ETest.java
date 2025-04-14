package com.bladestepapp.lifexpactivityservicetest.e2e.annotation;

import com.bladestepapp.lifexpactivityservicetest.config.MongoDbTestConfig;
import com.bladestepapp.lifexpactivityservicetest.config.TestConfig;
import com.bladestepapp.lifexpactivityservicetest.config.WireMockTestConfig;
import com.bladestepapp.lifexpactivityservicetest.extension.KafkaCleanupExtension;
import com.bladestepapp.lifexpactivityservicetest.extension.MongoCleanupExtension;
import com.bladestepapp.lifexpactivityservicetest.config.KafkaTestConfiguration;
import com.bladestepapp.lifexpactivityservicetest.extension.WireMockCleanupExtension;
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

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(
        classes = TestConfig.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ContextConfiguration(
        classes = KafkaTestConfiguration.class,
        initializers = WireMockTestConfig.class
)
@Import({MongoDbTestConfig.class, WireMockTestConfig.class})
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@ExtendWith({MongoCleanupExtension.class, KafkaCleanupExtension.class, WireMockCleanupExtension.class})
public @interface E2ETest {
} 