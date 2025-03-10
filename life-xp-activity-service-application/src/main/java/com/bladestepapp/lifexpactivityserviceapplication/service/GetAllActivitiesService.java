package com.bladestepapp.lifexpactivityserviceapplication.service;

import com.bladestepapp.lifexpactivityserviceapplication.mapper.ActivityResponseMapper;
import com.bladestepapp.lifexpactivityservicecore.domain.Activity;
import com.bladestepapp.lifexpactivityservicecore.model.ActivityResponseModel;
import com.bladestepapp.lifexpactivityservicecore.port.GetActivityPort;
import com.bladestepapp.lifexpactivityservicecore.usecase.GetAllActivitiesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllActivitiesService implements GetAllActivitiesUseCase {

    private final GetActivityPort getActivityPort;

    private final ActivityResponseMapper mapper;

    @Override
    public List<ActivityResponseModel> get() {
        List<Activity> activityList = getActivityPort.getAll();
        return mapper.map(activityList);
    }
}
