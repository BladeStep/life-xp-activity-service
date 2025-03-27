package com.bladestepapp.lifexpactivityserviceapi.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bladestepapp.lifexpactivityserviceapi.mapper.UserActivityMapper;
import com.bladestepapp.lifexpactivityserviceapi.mapper.UserActivityMapperImpl;
import com.bladestepapp.lifexpactivityservicecore.domain.enums.ActivityCategory;
import com.bladestepapp.lifexpactivityservicecore.domain.enums.ActivityUnit;
import com.bladestepapp.lifexpactivityservicecore.model.UserActivityResponseModel;
import com.bladestepapp.lifexpactivityservicecore.usecase.read.GetUserActivitiesUseCase;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@WebMvcTest()
@ContextConfiguration(classes = UserActivityQueryController.class)
@Import(UserActivityMapperImpl.class)
class UserActivityQueryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetUserActivitiesUseCase getUserActivitiesUseCase;

    @Autowired
    private UserActivityMapper mapper;

    @Test
    @SneakyThrows
    void getGetUserActivities() {
        //given
        UUID userId = UUID.randomUUID();
        UUID activityId1 = UUID.randomUUID();
        UUID activityId2 = UUID.randomUUID();

        UserActivityResponseModel firstActivity = new UserActivityResponseModel(
                activityId1,
                "Football",
                ActivityCategory.SPORT,
                ActivityUnit.HOURS,
                80,
                120);

        UserActivityResponseModel secondActivity = new UserActivityResponseModel(
                activityId2,
                "Reading",
                ActivityCategory.EDUCATION,
                ActivityUnit.SESSIONS,
                100,
                140);

        List<UserActivityResponseModel> userActivityResponseModels = Arrays.asList(firstActivity, secondActivity);

        when(getUserActivitiesUseCase.find(any())).thenReturn(userActivityResponseModels);

        //when,then
        mockMvc.perform(get("/api/user-activities")
                        .param("userId", userId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].activityId").value(activityId1.toString()))
                .andExpect(jsonPath("$[0].activityName").value("Football"))
                .andExpect(jsonPath("$[0].category").value(ActivityCategory.SPORT.name()))
                .andExpect(jsonPath("$[0].unit").value(ActivityUnit.HOURS.name()))
                .andExpect(jsonPath("$[0].baseXp").value(80))
                .andExpect(jsonPath("$[0].customXp").value(120))
                .andExpect(jsonPath("$[1].activityId").value(activityId2.toString()))
                .andExpect(jsonPath("$[1].activityName").value("Reading"))
                .andExpect(jsonPath("$[1].category").value(ActivityCategory.EDUCATION.name()))
                .andExpect(jsonPath("$[1].unit").value(ActivityUnit.SESSIONS.name()))
                .andExpect(jsonPath("$[1].baseXp").value(100))
                .andExpect(jsonPath("$[1].customXp").value(140));
    }
}