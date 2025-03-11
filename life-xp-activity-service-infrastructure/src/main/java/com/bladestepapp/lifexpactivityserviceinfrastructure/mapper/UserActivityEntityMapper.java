package com.bladestepapp.lifexpactivityserviceinfrastructure.mapper;

import com.bladestepapp.lifexpactivityservicecore.domain.UserActivity;
import com.bladestepapp.lifexpactivityserviceinfrastructure.entity.UserActivityEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserActivityEntityMapper {

    UserActivity map(UserActivityEntity userActivityEntity);

    UserActivityEntity map(UserActivity userActivity);
}
