package com.bladestepapp.lifexpactivityserviceapi.controller;

import com.bladestepapp.api.ActivityCommandApi;
import com.bladestepapp.lifexpactivityserviceapi.mapper.ActivityMapper;
import com.bladestepapp.lifexpactivityservicecore.command.CreateActivityCommand;
import com.bladestepapp.lifexpactivityservicecore.usecase.CreateActivityUseCase;
import com.bladestepapp.model.ActivityResponse;
import com.bladestepapp.model.CreateActivityRequest;
import com.bladestepapp.model.CreateActivityResponse;
import com.bladestepapp.model.MultiplierInput;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ActivityCommandController implements ActivityCommandApi {

    private final CreateActivityUseCase createActivityUseCase;

    private final ActivityMapper activityMapper;

    @Override
    public ResponseEntity<CreateActivityResponse> createActivity(CreateActivityRequest createActivityRequest) {
        CreateActivityCommand command = activityMapper.map(createActivityRequest);
        UUID activityId = createActivityUseCase.execute(command);
        CreateActivityResponse createActivityResponse = new CreateActivityResponse(activityId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createActivityResponse);
    }

    @Override
    public ResponseEntity<Void> deleteActivity(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> setActivityMultiplier(UUID id, MultiplierInput multiplierInput) {
        return null;
    }

    @Override
    public ResponseEntity<ActivityResponse> updateActivity(UUID id, CreateActivityRequest createActivityRequest) {
        return null;
    }
}
