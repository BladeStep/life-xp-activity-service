package com.bladestepapp.lifexpactivityserviceapi.controllers;

import com.bladestepapp.api.ActivityCommandApi;
import com.bladestepapp.lifexpactivityserviceapi.mappers.ActivityMapper;
import com.bladestepapp.lifexpactivityservicecore.commands.CreateActivityCommand;
import com.bladestepapp.lifexpactivityservicecore.usecases.CreateActivityUseCase;
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

//    @Override
//    public ResponseEntity<List<ActivityResponse>> getActivities() {
//        return null;
//    }
//
//
//    @Override
//    public ResponseEntity<MultiplierResponse> getActivityMultiplier(UUID id, UUID userId) {
//        return null;
//    }
//
//    @Override
//    public ResponseEntity<List<ActivityResponse>> getUserActivities(UUID userId) {
//        return null;
//    }

    @Override
    public ResponseEntity<Void> setActivityMultiplier(UUID id, MultiplierInput multiplierInput) {
        return null;
    }

    @Override
    public ResponseEntity<ActivityResponse> updateActivity(UUID id, CreateActivityRequest createActivityRequest) {
        return null;
    }
}
