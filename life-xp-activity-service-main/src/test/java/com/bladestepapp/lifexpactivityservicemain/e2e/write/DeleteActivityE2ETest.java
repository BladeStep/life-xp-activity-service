package com.bladestepapp.lifexpactivityservicemain.e2e.write;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

import com.bladestepapp.lifexpactivityserviceinfrastructure.entity.ActivityEntity;
import com.bladestepapp.lifexpactivityserviceinfrastructure.helper.EntityGenerator;
import com.bladestepapp.lifexpactivityserviceinfrastructure.persistence.ActivityRepository;
import com.bladestepapp.lifexpactivityservicemain.annotation.E2ETest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

@E2ETest
public class DeleteActivityE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void shouldDeleteActivity() {

        ActivityEntity activityEntity = EntityGenerator.createEntity();
        activityRepository.save(activityEntity);

        UUID activityId = activityEntity.getId();

        mockMvc.perform(delete("/activities/{id}", activityId))
                .andExpect(status().isNoContent());

        Optional<ActivityEntity> deletedActivity = activityRepository.findById(activityId);
        assertThat(deletedActivity).isEmpty();
    }
}
