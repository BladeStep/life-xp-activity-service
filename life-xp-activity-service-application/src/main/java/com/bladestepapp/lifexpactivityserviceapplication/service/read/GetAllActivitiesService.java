package com.bladestepapp.lifexpactivityserviceapplication.service.read;

import com.bladestepapp.lifexpactivityserviceapplication.mapper.ActivityResponseMapper;
import com.bladestepapp.lifexpactivityservicecore.domain.Activity;
import com.bladestepapp.lifexpactivityservicecore.model.ActivityResponseModel;
import com.bladestepapp.lifexpactivityservicecore.repository.GetActivityPort;
import com.bladestepapp.lifexpactivityservicecore.usecase.read.GetAllActivitiesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllActivitiesService implements GetAllActivitiesUseCase {

    private final GetActivityPort getActivityPort;

    private final ActivityResponseMapper mapper;

    @Override
    public List<ActivityResponseModel> find() {
        List<Activity> activityList = getActivityPort.findAll();
        return mapper.map(activityList);
    }
}
