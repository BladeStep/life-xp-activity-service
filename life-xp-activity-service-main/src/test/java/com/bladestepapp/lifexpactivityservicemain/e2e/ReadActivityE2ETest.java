package com.bladestepapp.lifexpactivityservicemain.e2e;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bladestepapp.lifexpactivityserviceinfrastructure.entities.ActivityEntity;
import com.bladestepapp.lifexpactivityserviceinfrastructure.helpers.EntityGenerator;
import com.bladestepapp.lifexpactivityserviceinfrastructure.repositories.ActivityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
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
