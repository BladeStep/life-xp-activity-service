package com.bladestepapp.lifexpactivityservicemain.e2e.read;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bladestepapp.lifexpactivityservicecore.domain.User;
import com.bladestepapp.lifexpactivityserviceinfrastructure.entity.ActivityEntity;
import com.bladestepapp.lifexpactivityserviceinfrastructure.entity.UserActivityEntity;
import com.bladestepapp.lifexpactivityserviceinfrastructure.helper.EntityGenerator;
import com.bladestepapp.lifexpactivityserviceinfrastructure.persistence.ActivityRepository;
import com.bladestepapp.lifexpactivityserviceinfrastructure.persistence.UserActivityRepository;
import com.bladestepapp.lifexpactivityservicemain.annotation.E2ETest;
import com.bladestepapp.model.UserActivityResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
        //given
        UUID userId = UUID.randomUUID();

        User user = User.create(userId, "John Doe", "john.doe@example.com");

        String userJson = objectMapper.writeValueAsString(user);

        stubUserGet(userId, userJson);

        ActivityEntity firstActivityEntity = EntityGenerator.createEntity("FOOTBALL", "Football game");
        ActivityEntity secondActivityEntity = EntityGenerator.createEntity("BASKETBALL", "Basketball game");
        ActivityEntity nonActivityEntity = EntityGenerator.createEntity("BASEBALL", "Baseball game");
        activityRepository.save(firstActivityEntity);
        activityRepository.save(secondActivityEntity);
        activityRepository.save(nonActivityEntity);

        UUID secondUserId = UUID.randomUUID();

        UserActivityEntity userActivityEntity1 = EntityGenerator.createUserActivityEntity(userId, firstActivityEntity.getId());
        UserActivityEntity userActivityEntity2 = EntityGenerator.createUserActivityEntity(userId, secondActivityEntity.getId());
        UserActivityEntity nonUserActivityEntity = EntityGenerator.createUserActivityEntity(secondUserId, nonActivityEntity.getId());

        userActivityRepository.save(userActivityEntity1);
        userActivityRepository.save(userActivityEntity2);
        userActivityRepository.save(nonUserActivityEntity);

        //when,then
        ResultActions result = mockMvc.perform( org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/user-activities")
                        .param("userId", userId.toString()))
                .andExpect(status().isOk());

        String responseContent = result.andReturn().getResponse().getContentAsString();

        List<UserActivityResponseDto> userActivityResponseList = Arrays.asList(
                objectMapper.readValue(responseContent, UserActivityResponseDto[].class));


        assertEquals(2, userActivityResponseList.size());

        UserActivityResponseDto expectedUserActivity1 = new UserActivityResponseDto();
        expectedUserActivity1.setActivityId(firstActivityEntity.getId());
        expectedUserActivity1.setActivityName(firstActivityEntity.getName());
        expectedUserActivity1.setCategory(com.bladestepapp.model.ActivityCategoryDto.SPORT);
        expectedUserActivity1.setUnit(com.bladestepapp.model.ActivityUnitDto.HOURS);
        expectedUserActivity1.setBaseXp(firstActivityEntity.getBaseXp());

        UserActivityResponseDto expectedUserActivity2 = new UserActivityResponseDto();
        expectedUserActivity2.setActivityId(secondActivityEntity.getId());
        expectedUserActivity2.setActivityName(secondActivityEntity.getName());
        expectedUserActivity2.setCategory(com.bladestepapp.model.ActivityCategoryDto.SPORT);
        expectedUserActivity2.setUnit(com.bladestepapp.model.ActivityUnitDto.HOURS);
        expectedUserActivity2.setBaseXp(secondActivityEntity.getBaseXp());

        assertThat(userActivityResponseList)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expectedUserActivity1, expectedUserActivity2);

    }

    private void stubUserGet(UUID userId, String body){
        stubFor(get(urlEqualTo("/user/" + userId))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(body)));
    }
}
