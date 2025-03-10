package com.bladestepapp.lifexpactivityserviceapplication.mapper;

import com.bladestepapp.lifexpactivityservicecore.domain.Activity;
import com.bladestepapp.lifexpactivityservicecore.model.ActivityResponseModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActivityResponseMapper {

    ActivityResponseModel map(Activity activity);

    List<ActivityResponseModel> map(List<Activity> activityList);
}