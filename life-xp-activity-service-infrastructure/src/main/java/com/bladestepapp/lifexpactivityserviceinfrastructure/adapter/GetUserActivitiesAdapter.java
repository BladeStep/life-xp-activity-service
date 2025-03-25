package com.bladestepapp.lifexpactivityserviceinfrastructure.adapter;

import com.bladestepapp.lifexpactivityserviceinfrastructure.entity.UserActivityEntity;
import com.bladestepapp.lifexpactivityserviceinfrastructure.mapper.UserActivityEntityMapper;
import com.bladestepapp.lifexpactivityserviceinfrastructure.persistence.UserActivityRepository;
import com.bladestepapp.lifexpactivityservicecore.domain.UserActivity;
import com.bladestepapp.lifexpactivityservicecore.persistence.GetUserActivitiesPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetUserActivitiesAdapter implements GetUserActivitiesPort {

    private final UserActivityRepository userActivityRepository;

    private final UserActivityEntityMapper mapper;

    @Override
    public List<UserActivity> findByUserId(UUID userId) {
        List<UserActivityEntity> activityEntityList = userActivityRepository.findByUserId(userId);
        return mapper.map(activityEntityList);
    }
}
