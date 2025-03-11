package com.bladestepapp.lifexpactivityserviceapi.mapper;

import com.bladestepapp.lifexpactivityservicecore.usecase.write.CreateActivityCommand;
import com.bladestepapp.lifexpactivityservicecore.model.ActivityResponseModel;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.CreateUserActivityCommand;
import com.bladestepapp.model.ActivityResponse;
import com.bladestepapp.model.CreateActivityRequest;
import com.bladestepapp.model.CreateUserActivityRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActivityMapper {

    CreateActivityCommand map(CreateActivityRequest createActivityRequest);

    CreateUserActivityCommand map(CreateUserActivityRequest addUserActivityRequest);

    ActivityResponse map(ActivityResponseModel activityResponseModel);

    List<ActivityResponse> map(List<ActivityResponseModel> activityResponseModel);

}