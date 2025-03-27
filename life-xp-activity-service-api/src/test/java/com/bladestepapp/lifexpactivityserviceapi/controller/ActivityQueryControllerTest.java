package com.bladestepapp.lifexpactivityserviceapi.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bladestepapp.lifexpactivityserviceapi.mapper.ActivityMapper;
import com.bladestepapp.lifexpactivityserviceapi.mapper.ActivityMapperImpl;
import com.bladestepapp.lifexpactivityservicecore.domain.enums.ActivityCategory;
import com.bladestepapp.lifexpactivityservicecore.domain.enums.ActivityUnit;
import com.bladestepapp.lifexpactivityservicecore.model.ActivityResponseModel;
import com.bladestepapp.lifexpactivityservicecore.usecase.read.GetActivityUseCase;
import com.bladestepapp.lifexpactivityservicecore.usecase.read.GetAllActivitiesUseCase;
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
@ContextConfiguration(classes = ActivityQueryController.class)
@Import(ActivityMapperImpl.class)
class ActivityQueryControllerTest {

    private static final String ACTIVITY_NAME = "Football";
    private static final String ACTIVITY_DESCRIPTION = "Football game";
    private static final int BASE_XP = 100;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetActivityUseCase getActivityUseCase;

    @MockBean
    private GetAllActivitiesUseCase getAllActivitiesUseCase;

    @Autowired
    private ActivityMapper activityMapper;

    @Test
    @SneakyThrows
    void shouldGetAllActivities() {
        //given
        ActivityResponseModel activity1 = new ActivityResponseModel(
                ACTIVITY_NAME,
                ACTIVITY_DESCRIPTION,
                ActivityCategory.SPORT,
                ActivityUnit.HOURS,
                BASE_XP);

        ActivityResponseModel activity2 = new ActivityResponseModel(
                "Reading",
                "Reading a book",
                ActivityCategory.EDUCATION,
                ActivityUnit.SESSIONS,
                3);

        List<ActivityResponseModel> activities = Arrays.asList(activity1, activity2);

        // Настройка мок-объекта
        when(getAllActivitiesUseCase.find()).thenReturn(activities);

        //when,then
        mockMvc.perform(get("/api/activities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))) // Проверка, что возвращается 2 активности
                .andExpect(jsonPath("$[0].name").value(activity1.getName()))
                .andExpect(jsonPath("$[0].description").value(activity1.getDescription()))
                .andExpect(jsonPath("$[0].category").value(activity1.getCategory().name()))
                .andExpect(jsonPath("$[0].unit").value(activity1.getUnit().name()))
                .andExpect(jsonPath("$[0].baseXp").value(activity1.getBaseXp()))
                .andExpect(jsonPath("$[1].name").value(activity2.getName()))
                .andExpect(jsonPath("$[1].description").value(activity2.getDescription()))
                .andExpect(jsonPath("$[1].category").value(activity2.getCategory().name()))
                .andExpect(jsonPath("$[1].unit").value(activity2.getUnit().name()))
                .andExpect(jsonPath("$[1].baseXp").value(activity2.getBaseXp()));
    }

    @Test
    @SneakyThrows
    void shoudlGetActivityById() {
        //given
        UUID activityId = UUID.randomUUID();
        ActivityResponseModel activityResponseModel = new ActivityResponseModel(
                ACTIVITY_NAME,
                ACTIVITY_DESCRIPTION,
                ActivityCategory.SPORT,
                ActivityUnit.HOURS,
                BASE_XP);


        when(getActivityUseCase.get(any())).thenReturn(activityResponseModel);

        //when,then
        mockMvc.perform(get("/api/activities/" + activityId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(ACTIVITY_NAME))
                .andExpect(jsonPath("$.description").value(ACTIVITY_DESCRIPTION))
                .andExpect(jsonPath("$.category").value(ActivityCategory.SPORT.name()))
                .andExpect(jsonPath("$.unit").value(ActivityUnit.HOURS.name()))
                .andExpect(jsonPath("$.baseXp").value(BASE_XP));
    }
}