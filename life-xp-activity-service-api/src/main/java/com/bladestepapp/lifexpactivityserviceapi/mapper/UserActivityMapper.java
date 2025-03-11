package com.bladestepapp.lifexpactivityserviceapi.mapper;

import com.bladestepapp.lifexpactivityservicecore.model.UserActivityResponseModel;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.CreateUserActivityCommand;
import com.bladestepapp.model.CreateUserActivityRequest;
import com.bladestepapp.model.UserActivityResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserActivityMapper {

    CreateUserActivityCommand map(CreateUserActivityRequest addUserActivityRequest);

    List<UserActivityResponse> map(List<UserActivityResponseModel> activityResponseModelList);
}