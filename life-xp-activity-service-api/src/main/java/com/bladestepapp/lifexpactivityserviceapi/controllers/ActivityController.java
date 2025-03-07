package com.bladestepapp.lifexpactivityserviceapi.controllers;

import com.bladestepapp.api.ActivitiesApi;
import com.bladestepapp.lifexpactivityserviceapi.mappers.ActivityMapper;
import com.bladestepapp.lifexpactivityservicecore.commands.CreateActivityCommand;
import com.bladestepapp.lifexpactivityservicecore.models.ActivityResponseModel;
import com.bladestepapp.lifexpactivityservicecore.queries.GetActivityQuery;
import com.bladestepapp.lifexpactivityservicecore.usecases.CreateActivityUseCase;
import com.bladestepapp.lifexpactivityservicecore.usecases.GetActivityUseCase;
import com.bladestepapp.model.ActivityResponse;
import com.bladestepapp.model.CreateActivityRequest;
import com.bladestepapp.model.CreateActivityResponse;
import com.bladestepapp.model.MultiplierInput;
import com.bladestepapp.model.MultiplierResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ActivityController implements ActivitiesApi {

    private final ActivityMapper activityMapper;
    private final CreateActivityUseCase createActivityUseCase;
    private final GetActivityUseCase getActivityUseCase;

    @Override
    public ResponseEntity<CreateActivityResponse> createActivity(CreateActivityRequest createActivityRequest) {
        CreateActivityCommand command = activityMapper.map(createActivityRequest);
        UUID activityId = createActivityUseCase.execute(command);
        CreateActivityResponse createActivityResponse = new CreateActivityResponse(activityId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createActivityResponse);
    }

    @Override
    public ResponseEntity<Void> deleteActivity(UUID id) {
        return ActivitiesApi.super.deleteActivity(id);
    }

    @Override
    public ResponseEntity<List<ActivityResponse>> getActivities() {
        return ActivitiesApi.super.getActivities();
    }

    @Override
    public ResponseEntity<ActivityResponse> getActivityById(UUID id) {
        GetActivityQuery query = new GetActivityQuery(id);
        ActivityResponseModel activityResponseModel = getActivityUseCase.get(query);
        ActivityResponse activityResponse = activityMapper.map(activityResponseModel);
        return ResponseEntity.ok(activityResponse);
    }

    @Override
    public ResponseEntity<MultiplierResponse> getActivityMultiplier(UUID id, UUID userId) {
        return ActivitiesApi.super.getActivityMultiplier(id, userId);
    }

    @Override
    public ResponseEntity<List<ActivityResponse>> getUserActivities(UUID userId) {
        return ActivitiesApi.super.getUserActivities(userId);
    }

    @Override
    public ResponseEntity<Void> setActivityMultiplier(UUID id, MultiplierInput multiplierInput) {
        return ActivitiesApi.super.setActivityMultiplier(id, multiplierInput);
    }

    @Override
    public ResponseEntity<ActivityResponse> updateActivity(UUID id, CreateActivityRequest activityInput) {
        return ActivitiesApi.super.updateActivity(id, activityInput);
    }
}
