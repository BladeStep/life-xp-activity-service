package com.bladestepapp.lifexpactivityservicemain.e2e.read;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

import com.bladestepapp.lifexpactivityserviceinfrastructure.entity.ActivityEntity;
import com.bladestepapp.lifexpactivityserviceinfrastructure.helper.EntityGenerator;
import com.bladestepapp.lifexpactivityserviceinfrastructure.persistence.ActivityRepository;
import com.bladestepapp.lifexpactivityservicemain.annotation.E2ETest;
import com.bladestepapp.model.ActivityResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

@E2ETest
public class ReadAllActivitiesE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnAllActivities_whenActivitiesExist() throws Exception {
        //given
        ActivityEntity activity1 = EntityGenerator.createEntity("FOOTBALL", "Football game");
        ActivityEntity activity2 = EntityGenerator.createEntity("BASKETBALL", "Basketball game");
        activityRepository.save(activity1);
        activityRepository.save(activity2);

        //when,then
        ResultActions result = mockMvc.perform(get("/activities"))
                .andExpect(status().isOk());

        String responseContent = result.andReturn().getResponse().getContentAsString();
        List<ActivityResponseDto> returnedActivities = Arrays.asList(
                objectMapper.readValue(responseContent, ActivityResponseDto[].class)
        );

        ActivityResponseDto expectedActivity1 = new ActivityResponseDto();
        expectedActivity1.setName("FOOTBALL");
        expectedActivity1.setDescription("Football game");
        expectedActivity1.setCategory(com.bladestepapp.model.ActivityCategoryDto.SPORT);
        expectedActivity1.setUnit(com.bladestepapp.model.ActivityUnitDto.HOURS);
        expectedActivity1.setBaseXp(50);

        ActivityResponseDto expectedActivity2 = new ActivityResponseDto();
        expectedActivity2.setName("BASKETBALL");
        expectedActivity2.setDescription("Basketball game");
        expectedActivity2.setCategory(com.bladestepapp.model.ActivityCategoryDto.SPORT);
        expectedActivity2.setUnit(com.bladestepapp.model.ActivityUnitDto.HOURS);
        expectedActivity2.setBaseXp(50);

        assertThat(returnedActivities)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expectedActivity1, expectedActivity2);
    }
}
