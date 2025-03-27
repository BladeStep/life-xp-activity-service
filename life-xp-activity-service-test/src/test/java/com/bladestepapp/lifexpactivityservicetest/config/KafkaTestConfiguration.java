package com.bladestepapp.lifexpactivityservicetest.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Конфигурация для E2E тестов с Kafka.
 */
@Slf4j
@TestConfiguration
public class KafkaTestConfiguration {

    private static final String KAFKA_IMAGE = "confluentinc/cp-kafka:7.4.0";

    private static final KafkaContainer KAFKA_CONTAINER = new KafkaContainer(DockerImageName.parse(KAFKA_IMAGE))
            .withKraft();

    static {
        log.info("Starting Kafka...");
        KAFKA_CONTAINER.start();
        log.info("Kafka started on: {}", KAFKA_CONTAINER.getBootstrapServers());
    }

    @Bean
    public KafkaContainer kafkaContainer() {
        return KAFKA_CONTAINER;
    }

    @Bean
    @Primary
    @DependsOn("kafkaContainer")
    public KafkaTemplate<String, Object> kafkaTemplate() {
        log.info("Configuring KafkaTemplate...");

        Map<String, Object> config = new HashMap<>();
        config.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                KAFKA_CONTAINER.getBootstrapServers());
        config.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        config.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);

        ProducerFactory<String, Object> producerFactory =
                new org.springframework.kafka.core.DefaultKafkaProducerFactory<>(config);
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    @Primary
    @DependsOn("kafkaContainer")
    public Consumer<String, Object> testKafkaConsumer() {
        log.info("Configuring Test Kafka Consumer...");

        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        JsonDeserializer<Object> jsonDeserializer = new JsonDeserializer<>();
        jsonDeserializer.addTrustedPackages("*");

        ConsumerFactory<String, Object> consumerFactory =
                new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), jsonDeserializer);

        return consumerFactory.createConsumer();
    }

    @Bean
    public AdminClient kafkaAdminClient() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", KAFKA_CONTAINER.getBootstrapServers());
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        return AdminClient.create(properties);
    }
}
