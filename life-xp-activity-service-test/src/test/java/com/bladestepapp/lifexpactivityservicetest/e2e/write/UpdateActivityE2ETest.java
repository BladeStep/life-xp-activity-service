package com.bladestepapp.lifexpactivityservicetest.e2e.write;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bladestepapp.lifexpactivityservicetest.e2e.annotation.E2ETest;
import com.bladestepapp.lifexpactivityservicecore.event.ActivityUpdatedEvent;
import com.bladestepapp.lifexpactivityserviceinfrastructure.entity.ActivityEntity;
import com.bladestepapp.lifexpactivityserviceinfrastructure.helper.EntityGenerator;
import com.bladestepapp.lifexpactivityserviceinfrastructure.persistence.ActivityRepository;
import com.bladestepapp.model.ActivityCategoryDto;
import com.bladestepapp.model.ActivityUnitDto;
import com.bladestepapp.model.UpdateActivityRequestDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.time.Duration;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@E2ETest
public class UpdateActivityE2ETest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private Consumer<String, Object> testKafkaConsumer;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldUpdateActivity() throws Exception {
        // given
        ActivityEntity activityEntity = EntityGenerator.createEntity();
        UUID activityId = activityEntity.getId();
        activityRepository.save(activityEntity);

        UpdateActivityRequestDto updateActivityRequestDto = new UpdateActivityRequestDto(
                "CHESS",
                "Chess game",
                ActivityCategoryDto.OTHER,
                ActivityUnitDto.SESSIONS,
                120);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(updateActivityRequestDto), headers);

        String topic = "activity.updates";

        testKafkaConsumer.subscribe(Collections.singletonList(topic));

        // when
        ResponseEntity<Void> response = restTemplate.exchange("/activities/{id}", HttpMethod.PUT, request, Void.class, activityId);

        // then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode(), "Response status should be 204 NO CONTENT");
        assertEquals(1, activityRepository.count());

        Optional<ActivityEntity> optionalUpdatedActivityEntity = activityRepository.findById(activityId);
        assertTrue(optionalUpdatedActivityEntity.isPresent());

        ActivityEntity updatedActivityEntity = optionalUpdatedActivityEntity.get();
        assertEquals(updateActivityRequestDto.getName(), updatedActivityEntity.getName());
        assertEquals(updateActivityRequestDto.getDescription(), updatedActivityEntity.getDescription());
        assertEquals(updateActivityRequestDto.getCategory().name(), updatedActivityEntity.getCategory().name());
        assertEquals(updateActivityRequestDto.getUnit().name(), updatedActivityEntity.getUnit().name());
        assertEquals(updateActivityRequestDto.getBaseXp(), updatedActivityEntity.getBaseXp());

        ConsumerRecords<String, Object> records = testKafkaConsumer.poll(Duration.ofSeconds(10));

        assertEquals(1, records.count(), "Kafka should receive 1 event");

        records.forEach(record -> {
            ActivityUpdatedEvent receivedEvent = (ActivityUpdatedEvent) record.value();
            assertEquals(activityId, receivedEvent.getActivityId());
            assertEquals("UPDATED", receivedEvent.getStatus());
        });

        testKafkaConsumer.close();
    }
}
