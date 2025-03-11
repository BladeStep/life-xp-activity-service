package com.bladestepapp.lifexpactivityserviceapi.controller;

import com.bladestepapp.api.ActivityCommandApi;
import com.bladestepapp.lifexpactivityserviceapi.mapper.ActivityMapper;
import com.bladestepapp.lifexpactivityserviceapi.mapper.UserActivityMapper;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.CreateActivityCommand;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.CreateActivityUseCase;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.CreateUserActivityCommand;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.CreateUserActivityUseCase;
import com.bladestepapp.model.ActivityResponse;
import com.bladestepapp.model.CreateActivityRequest;
import com.bladestepapp.model.CreateActivityResponse;
import com.bladestepapp.model.CreateUserActivityRequest;
import com.bladestepapp.model.CreateUserActivityResponse;
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

    private final CreateUserActivityUseCase createUserActivityUseCase;

    private final ActivityMapper mapper;

    private final UserActivityMapper userActivityMapper;

    @Override
    public ResponseEntity<CreateActivityResponse> createActivity(CreateActivityRequest createActivityRequest) {
        CreateActivityCommand command = mapper.map(createActivityRequest);
        UUID activityId = createActivityUseCase.execute(command);
        CreateActivityResponse createActivityResponse = new CreateActivityResponse(activityId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createActivityResponse);
    }

    @Override
    public ResponseEntity<Void> deleteActivity(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<CreateUserActivityResponse> addUserActivity(CreateUserActivityRequest createUserActivityRequest) {
        CreateUserActivityCommand command = userActivityMapper.map(createUserActivityRequest);
        UUID userActivityId = createUserActivityUseCase.execute(command);
        CreateUserActivityResponse createUserActivityResponse = new CreateUserActivityResponse(userActivityId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createUserActivityResponse);
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
