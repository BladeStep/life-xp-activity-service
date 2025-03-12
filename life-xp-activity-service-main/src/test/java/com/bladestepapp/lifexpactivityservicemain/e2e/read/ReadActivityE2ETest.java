package com.bladestepapp.lifexpactivityservicemain.e2e.read;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bladestepapp.lifexpactivityserviceinfrastructure.entity.ActivityEntity;
import com.bladestepapp.lifexpactivityserviceinfrastructure.entity.enums.ActivityCategory;
import com.bladestepapp.lifexpactivityserviceinfrastructure.entity.enums.ActivityUnit;
import com.bladestepapp.lifexpactivityserviceinfrastructure.helper.EntityGenerator;
import com.bladestepapp.lifexpactivityserviceinfrastructure.persistence.ActivityRepository;
import com.bladestepapp.lifexpactivityservicemain.annotation.E2ETest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

@E2ETest
public class ReadActivityE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ActivityRepository activityRepository;

    @Test
    void shouldReturnActivity_whenActivityExists() throws Exception {
        //given
        ActivityEntity activityEntity = EntityGenerator.createEntity();

        activityRepository.save(activityEntity);

        //when,then
        mockMvc.perform(get("/activities/{id}", activityEntity.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("FOOTBALL"))
                .andExpect(jsonPath("$.description").value("Football game"))
                .andExpect(jsonPath("$.category").value(ActivityCategory.SPORT.name()))
                .andExpect(jsonPath("$.unit").value(ActivityUnit.HOURS.name()))
                .andExpect(jsonPath("$.baseXp").value(50));
    }

    @Test
    void shouldReturnNotFound_whenActivityDoesNotExist() throws Exception {
        //given
        UUID activityId = UUID.randomUUID();

        //when,then
        mockMvc.perform(get("/activities/{id}", activityId))
                .andExpect(status().isNotFound());
    }
}
