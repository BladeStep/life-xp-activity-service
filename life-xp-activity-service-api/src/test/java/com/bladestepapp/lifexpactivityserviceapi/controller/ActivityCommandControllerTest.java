package com.bladestepapp.lifexpactivityserviceapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bladestepapp.lifexpactivityserviceapi.mapper.ActivityMapper;
import com.bladestepapp.lifexpactivityserviceapi.mapper.ActivityMapperImpl;
import com.bladestepapp.lifexpactivityservicecore.domain.enums.ActivityCategory;
import com.bladestepapp.lifexpactivityservicecore.domain.enums.ActivityUnit;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.CreateActivityCommand;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.CreateActivityUseCase;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.DeleteActivityCommand;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.DeleteActivityUseCase;
import com.bladestepapp.model.ActivityCategoryDto;
import com.bladestepapp.model.ActivityUnitDto;
import com.bladestepapp.model.CreateActivityRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

@WebMvcTest()
@ContextConfiguration(classes = ActivityCommandController.class)
@Import(ActivityMapperImpl.class)
class ActivityCommandControllerTest {

    private static final String ACTIVITY_NAME = "Football";
    private static final String ACTIVITY_DESCRIPTION = "Football game";
    private static final int BASE_XP = 100;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateActivityUseCase createActivityUseCase;

    @MockBean
    private DeleteActivityUseCase deleteActivityUseCase;

    @Autowired
    private ActivityMapper activityMapper;

    @Test
    @SneakyThrows
    void shouldCreateActivity() {
        //given
        UUID activityId = UUID.randomUUID();

        CreateActivityRequestDto createActivityRequestDto = new CreateActivityRequestDto(
                ACTIVITY_NAME,
                ACTIVITY_DESCRIPTION,
                ActivityCategoryDto.SPORT,
                ActivityUnitDto.HOURS,
                BASE_XP);

        when(createActivityUseCase.execute(any(CreateActivityCommand.class))).thenReturn(activityId);

        //when,then
        mockMvc.perform(post("/activities/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createActivityRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(activityId.toString()));

        verify(createActivityUseCase).execute(argThat(cmd ->
                cmd.getName().equals(ACTIVITY_NAME) &&
                        cmd.getDescription().equals(ACTIVITY_DESCRIPTION)
                        && cmd.getCategory().equals(ActivityCategory.SPORT)
                        && cmd.getUnit().equals(ActivityUnit.HOURS)
                        && cmd.getBaseXp() == BASE_XP
        ));
    }

    @Test
    @SneakyThrows
    void shouldDeleteActivity(){
        //given
        UUID activityId = UUID.randomUUID();
        doNothing().when(deleteActivityUseCase).execute(any(DeleteActivityCommand.class));

        //when,then
        mockMvc.perform(delete("/activities/{id}", activityId))
                .andExpect(status().isNoContent());
    }
}