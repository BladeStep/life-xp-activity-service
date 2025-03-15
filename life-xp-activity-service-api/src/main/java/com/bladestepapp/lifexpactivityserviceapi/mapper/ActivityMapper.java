package com.bladestepapp.lifexpactivityserviceapi.mapper;

import com.bladestepapp.lifexpactivityservicecore.model.ActivityResponseModel;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.CreateActivityCommand;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.UpdateActivityCommand;
import com.bladestepapp.model.ActivityResponseDto;
import com.bladestepapp.model.CreateActivityRequestDto;
import com.bladestepapp.model.UpdateActivityRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ActivityMapper {

    CreateActivityCommand map(CreateActivityRequestDto createActivityRequest);

    @Mapping(target = "id", source = "id")
    UpdateActivityCommand map(UUID id, UpdateActivityRequestDto createActivityRequest);

    ActivityResponseDto map(ActivityResponseModel activityResponseModel);

    List<ActivityResponseDto> map(List<ActivityResponseModel> activityResponseModel);

}