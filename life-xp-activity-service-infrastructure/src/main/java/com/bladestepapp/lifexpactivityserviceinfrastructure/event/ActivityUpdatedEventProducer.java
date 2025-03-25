package com.bladestepapp.lifexpactivityserviceinfrastructure.event;

import com.bladestepapp.lifexpactivityservicecore.event.ActivityUpdatedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ActivityUpdatedEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic.activity-updates}")
    private String topic;

    public ActivityUpdatedEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(ActivityUpdatedEvent event) {
        kafkaTemplate.send(topic, event);
    }
}