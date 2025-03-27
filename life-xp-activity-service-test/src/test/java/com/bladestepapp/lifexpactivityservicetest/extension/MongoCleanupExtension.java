package com.bladestepapp.lifexpactivityservicetest.extension;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Расширение для очистки коллекций MongoDB после каждого теста.
 */
@Slf4j
public class MongoCleanupExtension implements AfterEachCallback {

    private static final List<String> COLLECTIONS_TO_SKIP = List.of(
            "system.indexes",
            "system.views",
            "system.js"
    );

    @Override
    public void afterEach(ExtensionContext context) {
        log.info("Cleaning up MongoDB collections...");

        ApplicationContext applicationContext = SpringExtension.getApplicationContext(context);

        try {
            MongoTemplate mongoTemplate = applicationContext.getBean(MongoTemplate.class);

            Optional.of(mongoTemplate).ifPresent(template -> {
                Set<String> collectionNames = template.getCollectionNames();

                collectionNames.stream()
                        .filter(collection -> !COLLECTIONS_TO_SKIP.contains(collection))
                        .forEach(collection -> {
                            log.debug("Dropping collection: {}", collection);
                            template.dropCollection(collection);
                        });

                log.info("MongoDB cleanup completed successfully");
            });

        } catch (NoSuchBeanDefinitionException e) {
            log.warn("MongoTemplate bean not found, skipping MongoDB cleanup.");
        } catch (Exception e) {
            log.error("Error during MongoDB cleanup", e);
            throw new RuntimeException("Failed to clean up MongoDB collections", e);
        }
    }
}