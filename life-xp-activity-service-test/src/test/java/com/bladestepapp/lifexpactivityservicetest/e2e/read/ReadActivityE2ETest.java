package com.bladestepapp.lifexpactivityservicetest.e2e.read;

import static org.assertj.core.api.Assertions.assertThat;

import com.bladestepapp.lifexpactivityservicecore.domain.enums.ActivityCategory;
import com.bladestepapp.lifexpactivityservicecore.domain.enums.ActivityUnit;
import com.bladestepapp.lifexpactivityservicetest.e2e.annotation.E2ETest;
import com.bladestepapp.lifexpactivityserviceinfrastructure.entity.ActivityEntity;
import com.bladestepapp.lifexpactivityserviceinfrastructure.helper.EntityGenerator;
import com.bladestepapp.lifexpactivityserviceinfrastructure.persistence.ActivityRepository;
import com.bladestepapp.model.ActivityResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@E2ETest
public class ReadActivityE2ETest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ActivityRepository activityRepository;

    @Test
    void shouldReturnActivity_whenActivityExists() {
        // given
        ActivityEntity activityEntity = EntityGenerator.createEntity();
        activityRepository.save(activityEntity);

        // when
        ResponseEntity<ActivityResponseDto> response = restTemplate.getForEntity(
                "/api/activities/{id}",
                ActivityResponseDto.class,
                activityEntity.getId()
        );

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("FOOTBALL");
        assertThat(response.getBody().getDescription()).isEqualTo("Football game");
        assertThat(response.getBody().getCategory().name()).isEqualTo(ActivityCategory.SPORT.name());
        assertThat(response.getBody().getUnit().name()).isEqualTo(ActivityUnit.HOURS.name());
        assertThat(response.getBody().getBaseXp()).isEqualTo(50);
    }

    @Test
    void shouldReturnNotFound_whenActivityDoesNotExist() {
        // given
        UUID activityId = UUID.randomUUID();

        // when
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/api/activities/{id}",
                String.class,
                activityId
        );

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).contains("Activity with id " + activityId + " was not found");
    }
}
