package com.bladestepapp.lifexpactivityservicetest.e2e.read;

import static org.assertj.core.api.Assertions.assertThat;

import com.bladestepapp.lifexpactivityserviceinfrastructure.entity.ActivityEntity;
import com.bladestepapp.lifexpactivityserviceinfrastructure.entity.UserActivityEntity;
import com.bladestepapp.lifexpactivityserviceinfrastructure.helper.EntityGenerator;
import com.bladestepapp.lifexpactivityserviceinfrastructure.persistence.ActivityRepository;
import com.bladestepapp.lifexpactivityserviceinfrastructure.persistence.UserActivityRepository;
import com.bladestepapp.lifexpactivityservicetest.e2e.annotation.E2ETest;
import com.bladestepapp.model.UserActivityResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@E2ETest
class ReadUserActivityE2ETest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserActivityRepository userActivityRepository;

    @Test
    void shouldReturnUserActivities_whenUserHasActivities() {
        // given
        UUID userId = UUID.randomUUID();
        UUID otherUserId = UUID.randomUUID();

        ActivityEntity activity1 = EntityGenerator.createEntity();
        ActivityEntity activity2 = EntityGenerator.createEntity();
        ActivityEntity activity3 = EntityGenerator.createEntity();
        activityRepository.saveAll(List.of(activity1, activity2, activity3));

        UserActivityEntity userActivity1 = EntityGenerator.createUserActivityEntity(userId, activity1.getId());
        UserActivityEntity userActivity2 = EntityGenerator.createUserActivityEntity(userId, activity2.getId());

        UserActivityEntity otherUserActivity = EntityGenerator.createUserActivityEntity(otherUserId, activity3.getId());
        userActivityRepository.saveAll(List.of(userActivity1, userActivity2, otherUserActivity));

        // when
        String url = UriComponentsBuilder.fromPath("/api/user-activities")
                .queryParam("userId", userId)
                .toUriString();

        ResponseEntity<List<UserActivityResponseDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(2);

        UserActivityResponseDto firstUserActivity = response.getBody().get(0);
        assertThat(firstUserActivity.getActivityId()).isEqualTo(activity1.getId());

        UserActivityResponseDto secondUserActivity = response.getBody().get(1);
        assertThat(secondUserActivity.getActivityId()).isEqualTo(activity2.getId());
    }
}
