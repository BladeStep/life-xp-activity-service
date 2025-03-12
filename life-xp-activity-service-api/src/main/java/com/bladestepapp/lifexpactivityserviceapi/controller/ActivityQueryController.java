package com.bladestepapp.lifexpactivityserviceapi.controller;

import com.bladestepapp.api.ActivityQueryApi;
import com.bladestepapp.lifexpactivityserviceapi.mapper.ActivityMapper;
import com.bladestepapp.lifexpactivityservicecore.model.ActivityResponseModel;
import com.bladestepapp.lifexpactivityservicecore.usecase.read.*;
import com.bladestepapp.model.ActivityResponse;
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
        List<ActivityResponseModel> activityResponseModelList = getAllActivitiesUseCase.find();
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
}
