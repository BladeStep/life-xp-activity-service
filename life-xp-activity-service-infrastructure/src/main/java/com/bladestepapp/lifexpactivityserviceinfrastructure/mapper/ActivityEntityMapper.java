package com.bladestepapp.lifexpactivityserviceinfrastructure.mapper;

import com.bladestepapp.lifexpactivityservicecore.domain.Activity;
import com.bladestepapp.lifexpactivityserviceinfrastructure.entity.ActivityEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActivityEntityMapper {

    Activity map(ActivityEntity entity);

    ActivityEntity map(Activity activity);

    List<Activity> map(List<ActivityEntity> activityEntityList);
}