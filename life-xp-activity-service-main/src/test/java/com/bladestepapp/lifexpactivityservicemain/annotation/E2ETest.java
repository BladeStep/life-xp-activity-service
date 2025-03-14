package com.bladestepapp.lifexpactivityservicemain.annotation;

import com.bladestepapp.lifexpactivityservicemain.support.MongoCleanupExtension;
import com.bladestepapp.lifexpactivityservicemain.support.WireMockInitializer;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@ExtendWith(MongoCleanupExtension.class)
@ContextConfiguration(initializers = WireMockInitializer.class)
public @interface E2ETest {
}
