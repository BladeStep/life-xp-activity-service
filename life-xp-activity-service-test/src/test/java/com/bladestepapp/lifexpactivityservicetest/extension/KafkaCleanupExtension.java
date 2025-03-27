package com.bladestepapp.lifexpactivityservicetest.extension;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class KafkaCleanupExtension implements AfterEachCallback {

    private static final List<String> TOPICS_TO_SKIP = List.of(
            "__consumer_offsets"
    );

    @Override
    public void afterEach(ExtensionContext context) {
        log.info("Cleaning up Kafka topics...");

        try {
            AdminClient adminClient = SpringExtension.getApplicationContext(context).getBean(AdminClient.class);

            List<String> topics = adminClient.listTopics().names().get().stream()
                    .filter(topic -> !TOPICS_TO_SKIP.contains(topic))
                    .collect(Collectors.toList());

            if (!topics.isEmpty()) {
                adminClient.deleteTopics(topics).all().get();
                log.info("Kafka topics deleted: {}", topics);
            }

            log.info("Kafka cleanup completed successfully");
        } catch (Exception e) {
            log.error("Error during Kafka cleanup", e);
            throw new RuntimeException("Failed to clean up Kafka topics", e);
        }
    }
}