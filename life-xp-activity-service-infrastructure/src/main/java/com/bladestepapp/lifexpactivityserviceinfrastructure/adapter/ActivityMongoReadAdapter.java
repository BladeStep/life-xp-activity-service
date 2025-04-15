package com.bladestepapp.lifexpactivityserviceinfrastructure.adapter;

import com.bladestepapp.lifexpactivityserviceinfrastructure.entity.ActivityEntity;
import com.bladestepapp.lifexpactivityserviceinfrastructure.mapper.ActivityEntityMapper;
import com.bladestepapp.lifexpactivityserviceinfrastructure.persistence.ActivityRepository;
import com.bladestepapp.lifexpactivityservicecore.domain.Activity;
import com.bladestepapp.lifexpactivityservicecore.persistence.GetActivityPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ActivityMongoReadAdapter implements GetActivityPort {

    private final ActivityRepository activityRepository;

    private final ActivityEntityMapper mapper;

    @Override
    public Optional<Activity> find(UUID id) {
        Optional<ActivityEntity> activityEntity = activityRepository.findById(id);
        return activityEntity.map(mapper::map);
    }

    @Override
    public List<Activity> findAll() {
        List<ActivityEntity> activityEntityList = activityRepository.findAll();
        return mapper.map(activityEntityList);
    }
}
