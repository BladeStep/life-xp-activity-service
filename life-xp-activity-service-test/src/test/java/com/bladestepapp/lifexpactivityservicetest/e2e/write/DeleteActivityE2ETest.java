package com.bladestepapp.lifexpactivityservicetest.e2e.write;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

import com.bladestepapp.lifexpactivityserviceinfrastructure.entity.ActivityEntity;
import com.bladestepapp.lifexpactivityserviceinfrastructure.helper.EntityGenerator;
import com.bladestepapp.lifexpactivityserviceinfrastructure.persistence.ActivityRepository;
import com.bladestepapp.lifexpactivityservicetest.e2e.annotation.E2ETest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@E2ETest
public class DeleteActivityE2ETest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ActivityRepository activityRepository;

    @Test
    @SneakyThrows
    void shouldDeleteActivity() {
        // given
        ActivityEntity activityEntity = EntityGenerator.createEntity();
        activityRepository.save(activityEntity);
        UUID activityId = activityEntity.getId();

        // when
        ResponseEntity<Void> response = restTemplate.exchange("/activities/{id}", HttpMethod.DELETE, null, Void.class, activityId);

        // then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode(), "Response status should be 204 NO CONTENT");
        assertThat(activityRepository.findById(activityId)).isEmpty();
    }
}