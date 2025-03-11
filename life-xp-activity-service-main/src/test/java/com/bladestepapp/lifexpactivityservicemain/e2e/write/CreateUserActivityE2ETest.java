package com.bladestepapp.lifexpactivityservicemain.e2e.write;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bladestepapp.lifexpactivityserviceinfrastructure.repository.UserActivityRepository;
import com.bladestepapp.lifexpactivityservicemain.annotation.E2ETest;
import com.bladestepapp.model.CreateUserActivityRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

@E2ETest
class CreateUserActivityE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserActivityRepository userActivityRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void shouldSaveUserActivity() {

        UUID userId = UUID.randomUUID();
        UUID activityId = UUID.randomUUID();

        CreateUserActivityRequest userActivityRequest = new CreateUserActivityRequest(userId, activityId);
        userActivityRequest.setCustomXp(100);

        // Convert the request DTO to JSON
        String jsonRequest = objectMapper.writeValueAsString(userActivityRequest);


        // Perform POST request to create the activity
        mockMvc.perform(post("/user-activities/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    // Проверяем, что в ответе есть UUID
                    String responseBody = result.getResponse().getContentAsString();
                    assertTrue(responseBody.matches("\\{\\\"id\\\":\\\"[0-9a-fA-F-]{36}\\\"\\}"),
                            "Response should contain a valid UUID");
                });

        assertEquals(1, userActivityRepository.count(), "UserActivity should be saved in the database");
    }
}