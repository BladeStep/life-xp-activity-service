package com.bladestepapp.lifexpactivityserviceapi.mapper;

import com.bladestepapp.lifexpactivityservicecore.command.CreateActivityCommand;
import com.bladestepapp.lifexpactivityservicecore.model.ActivityResponseModel;
import com.bladestepapp.model.ActivityResponse;
import com.bladestepapp.model.CreateActivityRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActivityMapper {

    CreateActivityCommand map(CreateActivityRequest createActivityRequest);

    ActivityResponse map(ActivityResponseModel activityResponseModel);

    List<ActivityResponse> map(List<ActivityResponseModel> activityResponseModel);

}