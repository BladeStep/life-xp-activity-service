package com.bladestepapp.lifexpactivityserviceapi.mapper;

import com.bladestepapp.lifexpactivityservicecore.model.ActivityResponseModel;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.CreateActivityCommand;
import com.bladestepapp.model.ActivityResponseDto;
import com.bladestepapp.model.CreateActivityRequestDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActivityMapper {

    CreateActivityCommand map(CreateActivityRequestDto createActivityRequest);

    ActivityResponseDto map(ActivityResponseModel activityResponseModel);

    List<ActivityResponseDto> map(List<ActivityResponseModel> activityResponseModel);

}