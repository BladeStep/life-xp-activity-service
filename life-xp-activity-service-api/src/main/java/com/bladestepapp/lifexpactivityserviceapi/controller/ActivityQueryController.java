package com.bladestepapp.lifexpactivityserviceapi.controller;

import com.bladestepapp.api.ActivityQueryApi;
import com.bladestepapp.lifexpactivityserviceapi.mapper.ActivityMapper;
import com.bladestepapp.lifexpactivityservicecore.model.ActivityResponseModel;
import com.bladestepapp.lifexpactivityservicecore.usecase.read.GetActivityQuery;
import com.bladestepapp.lifexpactivityservicecore.usecase.read.GetActivityUseCase;
import com.bladestepapp.lifexpactivityservicecore.usecase.read.GetAllActivitiesUseCase;
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

    private final GetAllActivitiesUseCase getAllActivitiesUseCase;

    private final ActivityMapper mapper;

    @Override
    public ResponseEntity<List<ActivityResponse>> getActivities() {
        List<ActivityResponseModel> activityResponseModelList = getAllActivitiesUseCase.get();
        List<ActivityResponse> activityResponseList = mapper.map(activityResponseModelList);
        return ResponseEntity.ok(activityResponseList);
    }

    @Override
    public ResponseEntity<ActivityResponse> getActivityById(UUID id) {
        GetActivityQuery query = new GetActivityQuery(id);
        ActivityResponseModel activityResponseModel = getActivityUseCase.get(query);
        ActivityResponse activityResponse = mapper.map(activityResponseModel);
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
