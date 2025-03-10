package com.bladestepapp.lifexpactivityserviceapi.controllers;

import com.bladestepapp.api.ActivityQueryApi;
import com.bladestepapp.lifexpactivityserviceapi.mappers.ActivityMapper;
import com.bladestepapp.lifexpactivityservicecore.models.ActivityResponseModel;
import com.bladestepapp.lifexpactivityservicecore.queries.GetActivityQuery;
import com.bladestepapp.lifexpactivityservicecore.usecases.GetActivityUseCase;
import com.bladestepapp.model.ActivityResponse;
import com.bladestepapp.model.MultiplierResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ActivityQueryController implements ActivityQueryApi {

    private final GetActivityUseCase getActivityUseCase;

    private final ActivityMapper activityMapper;

    @Override
    public ResponseEntity<List<ActivityResponse>> getActivities() {
        return null;
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
        return null;
    }

    @Override
    public ResponseEntity<List<ActivityResponse>> getUserActivities(UUID userId) {
        return null;
    }
}
