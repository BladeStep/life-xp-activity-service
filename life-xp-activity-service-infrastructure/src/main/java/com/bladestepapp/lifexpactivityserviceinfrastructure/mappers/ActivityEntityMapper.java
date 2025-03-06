package com.bladestepapp.lifexpactivityserviceinfrastructure.mappers;

import com.bladestepapp.lifexpactivityservicecore.domain.Activity;
import com.bladestepapp.lifexpactivityserviceinfrastructure.entities.ActivityEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActivityEntityMapper {

    Activity map(ActivityEntity entity);

    ActivityEntity map(Activity activity);
}