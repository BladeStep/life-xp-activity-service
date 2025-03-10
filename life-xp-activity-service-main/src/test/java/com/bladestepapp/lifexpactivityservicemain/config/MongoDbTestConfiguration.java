package com.bladestepapp.lifexpactivityservicemain.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;

@Configuration
public class MongoDbTestConfiguration {

    private static final MongoDBContainer MONGO_DB_CONTAINER = new MongoDBContainer("mongo:6.0");

    static {
        MONGO_DB_CONTAINER.start();
    }

    @DynamicPropertySource
    static void mongoProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", MONGO_DB_CONTAINER::getReplicaSetUrl);
    }
}
