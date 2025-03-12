package com.bladestepapp.lifexpactivityserviceinfrastructure.adapter;

import com.bladestepapp.lifexpactivityservicecore.domain.Activity;
import com.bladestepapp.lifexpactivityservicecore.persistence.SaveActivityPort;
import com.bladestepapp.lifexpactivityserviceinfrastructure.entity.ActivityEntity;
import com.bladestepapp.lifexpactivityserviceinfrastructure.mapper.ActivityEntityMapper;
import com.bladestepapp.lifexpactivityserviceinfrastructure.persistence.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SaveActivityAdapter implements SaveActivityPort {

    private final ActivityRepository activityRepository;

    private final ActivityEntityMapper activityEntityMapper;

    @Override
    public UUID save(Activity activity) {
        ActivityEntity activityEntity = activityEntityMapper.map(activity);
        UUID activityId = activityEntity.getId() != null ? activityEntity.getId() : UUID.randomUUID();
        activityEntity.setId(activityId);
        ActivityEntity savedActivity = activityRepository.save(activityEntity);
        return savedActivity.getId();
    }
}
