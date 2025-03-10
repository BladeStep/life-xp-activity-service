package com.bladestepapp.lifexpactivityservicemain.e2e.write;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.bladestepapp.lifexpactivityserviceinfrastructure.repository.ActivityRepository;
import com.bladestepapp.lifexpactivityservicemain.annotation.E2ETest;
import com.bladestepapp.model.ActivityCategory;
import com.bladestepapp.model.ActivityUnit;
import com.bladestepapp.model.CreateActivityRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@E2ETest
class CreateActivityE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void shouldSaveActivity() {

        CreateActivityRequest activityRequest = new CreateActivityRequest(
                "Chess","Игра в шахматы", ActivityCategory.EDUCATION, ActivityUnit.SESSIONS, 10.0);

        // Convert the request DTO to JSON
        String jsonRequest = objectMapper.writeValueAsString(activityRequest);


        // Perform POST request to create the activity
        mockMvc.perform(post("/activities/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())  // Проверяем, что статус ответа 201 (Created)
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    // Проверяем, что в ответе есть UUID
                    String responseBody = result.getResponse().getContentAsString();
                    assertTrue(responseBody.matches("\\{\\\"id\\\":\\\"[0-9a-fA-F-]{36}\\\"\\}"),
                            "Response should contain a valid UUID");
                });


        // Проверка, что пользователь действительно создан в базе
        assertTrue(activityRepository.count() > 0, "Activity should be saved in the database");
    }
}