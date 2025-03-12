package com.bladestepapp.lifexpactivityserviceapi.mapper;

import com.bladestepapp.lifexpactivityservicecore.model.UserActivityResponseModel;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.CreateUserActivityCommand;
import com.bladestepapp.model.CreateUserActivityRequestDto;
import com.bladestepapp.model.UserActivityResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserActivityMapper {

    CreateUserActivityCommand map(CreateUserActivityRequestDto addUserActivityRequest);

    List<UserActivityResponseDto> map(List<UserActivityResponseModel> activityResponseModelList);
}