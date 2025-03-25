package com.bladestepapp.lifexpactivityservicetest.e2e.read;

import static org.assertj.core.api.Assertions.assertThat;

import com.bladestepapp.lifexpactivityservicetest.e2e.annotation.E2ETest;
import com.bladestepapp.lifexpactivityserviceinfrastructure.entity.ActivityEntity;
import com.bladestepapp.lifexpactivityserviceinfrastructure.helper.EntityGenerator;
import com.bladestepapp.lifexpactivityserviceinfrastructure.persistence.ActivityRepository;
import com.bladestepapp.model.ActivityResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@E2ETest
class ReadAllActivitiesE2ETest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ActivityRepository activityRepository;

    @Test
    void shouldReturnAllActivities() {
        // given
        ActivityEntity activity1 = EntityGenerator.createEntity();
        ActivityEntity activity2 = EntityGenerator.createEntity();

        activityRepository.saveAll(List.of(activity1, activity2));

        // when
        ResponseEntity<List<ActivityResponseDto>> response = restTemplate.exchange(
                "/activities",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(2);

        ActivityResponseDto firstActivity = response.getBody().get(0);
        assertThat(firstActivity.getName()).isEqualTo("FOOTBALL");
        assertThat(firstActivity.getDescription()).isEqualTo("Football game");

        ActivityResponseDto secondActivity = response.getBody().get(1);
        assertThat(secondActivity.getName()).isEqualTo("FOOTBALL");
        assertThat(secondActivity.getDescription()).isEqualTo("Football game");
    }

    @Test
    void shouldReturnEmptyList_whenNoActivitiesExist() {
        // when
        ResponseEntity<List<ActivityResponseDto>> response = restTemplate.exchange(
                "/activities",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ActivityResponseDto>>() {}
        );

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEmpty();
    }
}
