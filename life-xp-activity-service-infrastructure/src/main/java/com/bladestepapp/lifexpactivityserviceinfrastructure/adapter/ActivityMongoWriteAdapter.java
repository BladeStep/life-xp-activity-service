package com.bladestepapp.lifexpactivityserviceinfrastructure.adapter;

import com.bladestepapp.lifexpactivityserviceinfrastructure.entity.ActivityEntity;
import com.bladestepapp.lifexpactivityserviceinfrastructure.mapper.ActivityEntityMapper;
import com.bladestepapp.lifexpactivityserviceinfrastructure.persistence.ActivityRepository;
import com.bladestepapp.lifexpactivityservicecore.domain.Activity;
import com.bladestepapp.lifexpactivityservicecore.persistence.SaveActivityPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ActivityMongoWriteAdapter implements SaveActivityPort {

    private final ActivityRepository activityRepository;

    private final ActivityEntityMapper activityEntityMapper;

    @Override
    public UUID save(Activity activity) {
        ActivityEntity activityEntity = activityEntityMapper.map(activity);

        UUID activityId = Optional.ofNullable(activityEntity.getId()).orElse(UUID.randomUUID());
        activityEntity.setId(activityId);

        return activityRepository.save(activityEntity).getId();
    }
}
