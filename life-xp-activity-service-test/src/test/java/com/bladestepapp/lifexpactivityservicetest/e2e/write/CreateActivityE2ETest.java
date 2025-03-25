package com.bladestepapp.lifexpactivityservicetest.e2e.write;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.bladestepapp.lifexpactivityservicetest.e2e.annotation.E2ETest;
import com.bladestepapp.lifexpactivityserviceinfrastructure.persistence.ActivityRepository;
import com.bladestepapp.model.ActivityCategoryDto;
import com.bladestepapp.model.ActivityUnitDto;
import com.bladestepapp.model.CreateActivityRequestDto;
import com.bladestepapp.model.CreateActivityResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@E2ETest
class CreateActivityE2ETest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void shouldSaveActivity() {
        // given
        CreateActivityRequestDto activityRequest = new CreateActivityRequestDto(
                "Chess", "Игра в шахматы", ActivityCategoryDto.EDUCATION, ActivityUnitDto.SESSIONS, 10);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(activityRequest), headers);

        // when
        ResponseEntity<CreateActivityResponseDto> response = restTemplate.postForEntity("/activities/create", request, CreateActivityResponseDto.class);

        // then
        assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response status should be 201 CREATED");
        assertNotNull(response.getBody(), "Response body should not be null");

        assertTrue(activityRepository.count() > 0, "Activity should be saved in the database");
    }
}