package com.bladestepapp.lifexpactivityserviceapi.controller;

import com.bladestepapp.api.UserActivityQueryApi;
import com.bladestepapp.lifexpactivityserviceapi.mapper.UserActivityMapper;
import com.bladestepapp.lifexpactivityservicecore.model.UserActivityResponseModel;
import com.bladestepapp.lifexpactivityservicecore.usecase.read.GetUserActivitiesUseCase;
import com.bladestepapp.lifexpactivityservicecore.usecase.read.GetUserActivityQuery;
import com.bladestepapp.model.UserActivityResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserActivityQueryController implements UserActivityQueryApi {

    private final GetUserActivitiesUseCase getUserActivitiesUseCase;

    private final UserActivityMapper mapper;

    @Override
    public ResponseEntity<List<UserActivityResponseDto>> getUserActivities(@RequestParam UUID userId) {
        GetUserActivityQuery query = new GetUserActivityQuery(userId);
        List<UserActivityResponseModel> userActivityResponseModelList = getUserActivitiesUseCase.find(query);
        List<UserActivityResponseDto> userActivityResponseDtoList = mapper.map(userActivityResponseModelList);
        return ResponseEntity.ok(userActivityResponseDtoList);
    }
}
