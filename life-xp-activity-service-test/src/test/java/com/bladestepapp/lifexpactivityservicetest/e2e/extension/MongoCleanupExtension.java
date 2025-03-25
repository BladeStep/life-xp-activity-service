package com.bladestepapp.lifexpactivityservicetest.e2e.extension;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

/**
 * Расширение для очистки коллекций MongoDB после каждого теста.
 */
@Slf4j
public class MongoCleanupExtension implements AfterEachCallback {

    private static final List<String> COLLECTIONS_TO_SKIP = Arrays.asList(
            "system.indexes",
            "system.views",
            "system.js"
    );

    @Override
    public void afterEach(ExtensionContext context) {
        log.info("Cleaning up MongoDB collections...");
        
        try {
            MongoTemplate mongoTemplate = SpringExtension.getApplicationContext(context)
                    .getBean(MongoTemplate.class);

            mongoTemplate.getCollectionNames()
                    .stream()
                    .filter(collection -> !COLLECTIONS_TO_SKIP.contains(collection))
                    .forEach(collection -> {
                        log.debug("Dropping collection: {}", collection);
                        mongoTemplate.dropCollection(collection);
                    });

            log.info("MongoDB cleanup completed successfully");
        } catch (Exception e) {
            log.error("Error during MongoDB cleanup", e);
            throw new RuntimeException("Failed to clean up MongoDB collections", e);
        }
    }
} 