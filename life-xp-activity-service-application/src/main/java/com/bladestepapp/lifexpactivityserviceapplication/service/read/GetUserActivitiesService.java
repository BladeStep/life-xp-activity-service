package com.bladestepapp.lifexpactivityserviceapplication.service.read;

import com.bladestepapp.lifexpactivityserviceapplication.mapper.UserActivityResponseMapper;
import com.bladestepapp.lifexpactivityservicecore.domain.Activity;
import com.bladestepapp.lifexpactivityservicecore.exception.ActivityNotFoundException;
import com.bladestepapp.lifexpactivityservicecore.model.UserActivityResponseModel;
import com.bladestepapp.lifexpactivityservicecore.persistence.GetActivityPort;
import com.bladestepapp.lifexpactivityservicecore.persistence.GetUserActivitiesPort;
import com.bladestepapp.lifexpactivityservicecore.usecase.read.GetUserActivitiesUseCase;
import com.bladestepapp.lifexpactivityservicecore.usecase.read.GetUserActivityQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetUserActivitiesService implements GetUserActivitiesUseCase {

    private final GetUserActivitiesPort getUserActivitiesPort;

    private final GetActivityPort getActivityPort;

    private final UserActivityResponseMapper mapper;

    @Override
    public List<UserActivityResponseModel> find(GetUserActivityQuery query) {
        return getUserActivitiesPort.findByUserId(query.getUserId()).stream()
                .map(userActivity -> {
                    Activity activity = getActivityPort.find(userActivity.getActivityId())
                            .orElseThrow(() -> new ActivityNotFoundException(
                                    "Activity with id " + userActivity.getActivityId() + " was not found"));
                    return mapper.map(userActivity, activity);
                }).toList();
    }
}
