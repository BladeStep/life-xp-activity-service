package com.bladestepapp.lifexpactivityserviceapplication.mapper;

import com.bladestepapp.lifexpactivityservicecore.domain.Activity;
import com.bladestepapp.lifexpactivityservicecore.domain.UserActivity;
import com.bladestepapp.lifexpactivityservicecore.model.UserActivityResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserActivityResponseMapper {

    @Mappings({
            @Mapping(source = "userActivity.activityId", target = "activityId"),
            @Mapping(source = "userActivity.customXp", target = "customXp"),
            @Mapping(source = "activity.name", target = "activityName"),
            @Mapping(source = "activity.category", target = "category"),
            @Mapping(source = "activity.unit", target = "unit"),
            @Mapping(source = "activity.baseXp", target = "baseXp"),
    })
    UserActivityResponseModel map(UserActivity userActivity, Activity activity);
}
