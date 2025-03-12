package com.bladestepapp.lifexpactivityserviceapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bladestepapp.lifexpactivityserviceapi.mapper.UserActivityMapper;
import com.bladestepapp.lifexpactivityserviceapi.mapper.UserActivityMapperImpl;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.CreateUserActivityCommand;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.CreateUserActivityUseCase;
import com.bladestepapp.model.CreateUserActivityRequestDto;
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
@ContextConfiguration(classes = UserActivityCommandController.class)
@Import(UserActivityMapperImpl.class)
class UserActivityCommandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateUserActivityUseCase createUserActivityUseCase;

    @Autowired
    private UserActivityMapper mapper;

    @Test
    @SneakyThrows
    void shouldAddUserActivity() {
        //given
        UUID userId = UUID.randomUUID();
        UUID activityId = UUID.randomUUID();
        UUID userActivityId = UUID.randomUUID();
        int customXp = 12;

        CreateUserActivityRequestDto createUserActivityRequestDto = new CreateUserActivityRequestDto(userId, activityId);
        createUserActivityRequestDto.setCustomXp(customXp);

        when(createUserActivityUseCase.execute(any(CreateUserActivityCommand.class))).thenReturn(userActivityId);

        //when,then
        mockMvc.perform(post("/user-activities/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createUserActivityRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(userActivityId.toString()));

        verify(createUserActivityUseCase).execute(argThat(cmd ->
                cmd.getUserId().equals(userId) &&
                        cmd.getActivityId().equals(activityId)
                        && cmd.getCustomXp() == customXp
        ));
    }
}