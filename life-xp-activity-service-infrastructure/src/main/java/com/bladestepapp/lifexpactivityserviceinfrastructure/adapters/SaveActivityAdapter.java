package com.bladestepapp.lifexpactivityserviceinfrastructure.adapters;

import com.bladestepapp.lifexpactivityservicecore.domain.Activity;
import com.bladestepapp.lifexpactivityservicecore.ports.SaveActivityPort;
import com.bladestepapp.lifexpactivityserviceinfrastructure.entities.ActivityEntity;
import com.bladestepapp.lifexpactivityserviceinfrastructure.mappers.ActivityEntityMapper;
import com.bladestepapp.lifexpactivityserviceinfrastructure.repositories.ActivityRepository;
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
