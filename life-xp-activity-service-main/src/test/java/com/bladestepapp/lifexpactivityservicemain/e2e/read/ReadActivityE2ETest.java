package com.bladestepapp.lifexpactivityservicemain.e2e.read;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bladestepapp.lifexpactivityserviceinfrastructure.entity.ActivityEntity;
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
        ActivityEntity activityEntity = EntityGenerator.createEntity();

        activityRepository.save(activityEntity);

        mockMvc.perform(get("/activities/{id}", activityEntity.getId()))
                .andExpect(status().isOk())  // Ожидаем, что статус будет OK
                .andExpect(jsonPath("$.name").value("FOOTBALL"))
                .andExpect(jsonPath("$.description").value("Football game"));
    }

    @Test
    void shouldReturnNotFound_whenActivityDoesNotExist() throws Exception {
        // Отправляем GET запрос на несуществующий ID
        mockMvc.perform(get("/activities/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound());  // Ожидаем, что вернется статус 404
    }
}
