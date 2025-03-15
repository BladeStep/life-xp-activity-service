package com.bladestepapp.lifexpactivityservicemain.e2e.write;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bladestepapp.lifexpactivityserviceinfrastructure.entity.ActivityEntity;
import com.bladestepapp.lifexpactivityserviceinfrastructure.helper.EntityGenerator;
import com.bladestepapp.lifexpactivityserviceinfrastructure.persistence.ActivityRepository;
import com.bladestepapp.lifexpactivityservicemain.annotation.E2ETest;
import com.bladestepapp.model.ActivityCategoryDto;
import com.bladestepapp.model.ActivityUnitDto;
import com.bladestepapp.model.UpdateActivityRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

@E2ETest
public class UpdateActivityE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void shouldUpdateActivity() {
        //given
        ActivityEntity activityEntity = EntityGenerator.createEntity();
        UUID activityId = activityEntity.getId();

        activityRepository.save(activityEntity);

        UpdateActivityRequestDto updateActivityRequestDto = new UpdateActivityRequestDto(
                "CHESS",
                "Chess game",
                ActivityCategoryDto.OTHER,
                ActivityUnitDto.SESSIONS,
                120);

        //when,then
        mockMvc.perform(put("/activities/{id}", activityId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateActivityRequestDto)))
                .andExpect(status().isNoContent());

        assertEquals(1, activityRepository.count());

        Optional<ActivityEntity> optionalUpdatedActivityEntity = activityRepository.findById(activityId);

        assertTrue(optionalUpdatedActivityEntity.isPresent());

        ActivityEntity updatedActivityEntity = optionalUpdatedActivityEntity.get();
        assertEquals(updateActivityRequestDto.getName(), updatedActivityEntity.getName());
        assertEquals(updateActivityRequestDto.getDescription(), updatedActivityEntity.getDescription());
        assertEquals(updateActivityRequestDto.getCategory().name(), updatedActivityEntity.getCategory().name());
        assertEquals(updateActivityRequestDto.getUnit().name(), updatedActivityEntity.getUnit().name());
        assertEquals(updateActivityRequestDto.getBaseXp(), updatedActivityEntity.getBaseXp());
    }
}
