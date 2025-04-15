package com.bladestepapp.lifexpactivityserviceinfrastructure.adapter;

import com.bladestepapp.lifexpactivityserviceinfrastructure.entity.UserActivityEntity;
import com.bladestepapp.lifexpactivityserviceinfrastructure.mapper.UserActivityEntityMapper;
import com.bladestepapp.lifexpactivityserviceinfrastructure.persistence.UserActivityRepository;
import com.bladestepapp.lifexpactivityservicecore.domain.UserActivity;
import com.bladestepapp.lifexpactivityservicecore.persistence.SaveUserActivityPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserActivityMongoWriteAdapter implements SaveUserActivityPort {

    private final UserActivityRepository userActivityRepository;

    private final UserActivityEntityMapper userActivityEntityMapper;

    @Override
    public UUID save(UserActivity userActivity) {
        UserActivityEntity userActivityEntity = userActivityEntityMapper.map(userActivity);
        UUID userActivityId = userActivityEntity.getId() != null ? userActivityEntity.getId() : UUID.randomUUID();
        userActivityEntity.setId(userActivityId);
        UserActivityEntity savedUserActivity = userActivityRepository.save(userActivityEntity);
        return savedUserActivity.getId();
    }
}
