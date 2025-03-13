package com.bladestepapp.lifexpactivityservicemain.e2e.write;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.bladestepapp.lifexpactivityserviceinfrastructure.persistence.ActivityRepository;
import com.bladestepapp.lifexpactivityservicemain.annotation.E2ETest;
import com.bladestepapp.lifexpactivityservicemain.support.WireMockInitializer;
import com.bladestepapp.model.ActivityCategoryDto;
import com.bladestepapp.model.ActivityUnitDto;
import com.bladestepapp.model.CreateActivityRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

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
        //given
        CreateActivityRequestDto activityRequest = new CreateActivityRequestDto(
                "Chess","Игра в шахматы", ActivityCategoryDto.EDUCATION, ActivityUnitDto.SESSIONS, 10);

        String jsonRequest = objectMapper.writeValueAsString(activityRequest);

        //when,then
        mockMvc.perform(post("/activities/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())  // Проверяем, что статус ответа 201 (Created)
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    String responseBody = result.getResponse().getContentAsString();
                    assertTrue(responseBody.matches("\\{\\\"id\\\":\\\"[0-9a-fA-F-]{36}\\\"\\}"),
                            "Response should contain a valid UUID");
                });

        assertTrue(activityRepository.count() > 0, "Activity should be saved in the database");
    }
}