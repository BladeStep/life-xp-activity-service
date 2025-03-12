package com.bladestepapp.lifexpactivityservicemain.e2e.read;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bladestepapp.lifexpactivityserviceinfrastructure.entity.ActivityEntity;
import com.bladestepapp.lifexpactivityserviceinfrastructure.entity.UserActivityEntity;
import com.bladestepapp.lifexpactivityserviceinfrastructure.helper.EntityGenerator;
import com.bladestepapp.lifexpactivityserviceinfrastructure.persistence.ActivityRepository;
import com.bladestepapp.lifexpactivityserviceinfrastructure.persistence.UserActivityRepository;
import com.bladestepapp.lifexpactivityservicemain.annotation.E2ETest;
import com.bladestepapp.model.UserActivityResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@E2ETest
public class ReadUserActivityE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserActivityRepository userActivityRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void shouldReturnUserActivity() {

        ActivityEntity firstActivityEntity = EntityGenerator.createEntity("FOOTBALL", "Football game");
        ActivityEntity secondActivityEntity = EntityGenerator.createEntity("BASKETBALL", "Basketball game");
        ActivityEntity nonActivityEntity = EntityGenerator.createEntity("BASEBALL", "Baseball game");
        activityRepository.save(firstActivityEntity);
        activityRepository.save(secondActivityEntity);
        activityRepository.save(nonActivityEntity);

        UUID userId = UUID.randomUUID();
        UUID secondUserId = UUID.randomUUID();

        UserActivityEntity userActivityEntity1 = EntityGenerator.createUserActivityEntity(userId, firstActivityEntity.getId());
        UserActivityEntity userActivityEntity2 = EntityGenerator.createUserActivityEntity(userId, secondActivityEntity.getId());
        UserActivityEntity nonUserActivityEntity = EntityGenerator.createUserActivityEntity(secondUserId, nonActivityEntity.getId());

        userActivityRepository.save(userActivityEntity1);
        userActivityRepository.save(userActivityEntity2);
        userActivityRepository.save(nonUserActivityEntity);

        ResultActions result = mockMvc.perform(get("/user-activities/{userId}", userId))
                .andExpect(status().isOk());

        String responseContent = result.andReturn().getResponse().getContentAsString();

        List<UserActivityResponse> userActivityResponseList = Arrays.asList(
                objectMapper.readValue(responseContent, UserActivityResponse[].class));


        assertEquals(2, userActivityResponseList.size());

        UserActivityResponse expectedUserActivity1 = new UserActivityResponse();
        expectedUserActivity1.setActivityId(firstActivityEntity.getId());
        expectedUserActivity1.setActivityName(firstActivityEntity.getName());
        expectedUserActivity1.setCategory(com.bladestepapp.model.ActivityCategory.SPORT);
        expectedUserActivity1.setUnit(com.bladestepapp.model.ActivityUnit.HOURS);
        expectedUserActivity1.setBaseXp(firstActivityEntity.getBaseXp());

        UserActivityResponse expectedUserActivity2 = new UserActivityResponse();
        expectedUserActivity2.setActivityId(secondActivityEntity.getId());
        expectedUserActivity2.setActivityName(secondActivityEntity.getName());
        expectedUserActivity2.setCategory(com.bladestepapp.model.ActivityCategory.SPORT);
        expectedUserActivity2.setUnit(com.bladestepapp.model.ActivityUnit.HOURS);
        expectedUserActivity2.setBaseXp(secondActivityEntity.getBaseXp());

        assertThat(userActivityResponseList)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expectedUserActivity1, expectedUserActivity2);

    }
}
