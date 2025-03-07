package com.bladestepapp.lifexpactivityserviceinfrastructure.adapters;

import com.bladestepapp.lifexpactivityservicecore.domain.Activity;
import com.bladestepapp.lifexpactivityservicecore.ports.GetActivityPort;
import com.bladestepapp.lifexpactivityserviceinfrastructure.entities.ActivityEntity;
import com.bladestepapp.lifexpactivityserviceinfrastructure.mappers.ActivityEntityMapper;
import com.bladestepapp.lifexpactivityserviceinfrastructure.repositories.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetActivityAdapter implements GetActivityPort {

    private final ActivityRepository activityRepository;

    private final ActivityEntityMapper mapper;

    @Override
    public Optional<Activity> get(UUID id) {
        Optional<ActivityEntity> activityEntity = activityRepository.findById(id);
        return activityEntity.map(mapper::map);
    }
}
