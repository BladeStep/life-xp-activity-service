package com.bladestepapp.lifexpactivityserviceapplication.services;

import com.bladestepapp.lifexpactivityserviceapplication.mappers.ActivityResponseMapper;
import com.bladestepapp.lifexpactivityservicecore.domain.Activity;
import com.bladestepapp.lifexpactivityservicecore.exceptions.ActivityNotFoundException;
import com.bladestepapp.lifexpactivityservicecore.models.ActivityResponseModel;
import com.bladestepapp.lifexpactivityservicecore.ports.GetActivityPort;
import com.bladestepapp.lifexpactivityservicecore.queries.GetActivityQuery;
import com.bladestepapp.lifexpactivityservicecore.usecases.GetActivityUseCase;
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
        Activity activity = getActivityPort.get(activityId)
                .orElseThrow(() -> new ActivityNotFoundException("Activity with id " + activityId + " was not found"));
        return mapper.map(activity);
    }
}
