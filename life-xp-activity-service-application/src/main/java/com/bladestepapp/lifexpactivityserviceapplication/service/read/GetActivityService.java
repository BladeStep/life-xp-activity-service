package com.bladestepapp.lifexpactivityserviceapplication.service.read;

import com.bladestepapp.lifexpactivityserviceapplication.mapper.ActivityResponseMapper;
import com.bladestepapp.lifexpactivityservicecore.domain.Activity;
import com.bladestepapp.lifexpactivityservicecore.exception.ActivityNotFoundException;
import com.bladestepapp.lifexpactivityservicecore.model.ActivityResponseModel;
import com.bladestepapp.lifexpactivityservicecore.persistence.GetActivityPort;
import com.bladestepapp.lifexpactivityservicecore.usecase.read.GetActivityQuery;
import com.bladestepapp.lifexpactivityservicecore.usecase.read.GetActivityUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetActivityService implements GetActivityUseCase {

    private final GetActivityPort getActivityPort;

    private final ActivityResponseMapper mapper;

    @Override
    public ActivityResponseModel get(GetActivityQuery query) {
        UUID activityId = query.getId();
        Activity activity = getActivityPort.find(activityId)
                .orElseThrow(() -> new ActivityNotFoundException("Activity with id " + activityId + " was not found"));
        return mapper.map(activity);
    }
}
