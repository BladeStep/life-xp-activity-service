package com.bladestepapp.lifexpactivityservicetest.e2e.write;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.bladestepapp.lifexpactivityservicecore.domain.User;
import com.bladestepapp.lifexpactivityserviceinfrastructure.entity.ActivityEntity;
import com.bladestepapp.lifexpactivityserviceinfrastructure.helper.EntityGenerator;
import com.bladestepapp.lifexpactivityserviceinfrastructure.persistence.ActivityRepository;
import com.bladestepapp.lifexpactivityserviceinfrastructure.persistence.UserActivityRepository;
import com.bladestepapp.lifexpactivityservicetest.e2e.annotation.E2ETest;
import com.bladestepapp.model.CreateUserActivityRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@E2ETest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreateUserActivityE2ETest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserActivityRepository userActivityRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WireMock wireMock;

    @Test
    void shouldSaveUserActivity() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID activityId = UUID.randomUUID();

        stubUserExists(userId);

        CreateUserActivityRequestDto request = new CreateUserActivityRequestDto(userId, activityId);
        request.setCustomXp(100);

        ActivityEntity activityEntity = EntityGenerator.createEntity();
        activityEntity.setId(activityId);
        activityRepository.save(activityEntity);

        ResponseEntity<String> response = restTemplate.postForEntity("/api/user-activities/create", request, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, userActivityRepository.count(), "UserActivity should be saved in the database");
    }

    @Test
    void shouldReturnNotFoundWhenUserDoesNotExist() {
        UUID userId = UUID.randomUUID();
        UUID activityId = UUID.randomUUID();

        stubUserNotFound(userId);

        CreateUserActivityRequestDto request = new CreateUserActivityRequestDto(userId, activityId);
        request.setCustomXp(100);

        ResponseEntity<String> response = restTemplate.postForEntity("/user-activities/create", request, String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    private void stubUserExists(UUID userId) throws Exception {
        User user = User.create(userId, "John Doe", "john.doe@example.com");
        String userJson = objectMapper.writeValueAsString(user);
        wireMock.register(get(urlEqualTo("/api/user/" + userId))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(userJson)));
    }

    private void stubUserNotFound(UUID userId) {
        wireMock.register(get(urlEqualTo("/api/user/" + userId))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody("User not found")));
    }
}