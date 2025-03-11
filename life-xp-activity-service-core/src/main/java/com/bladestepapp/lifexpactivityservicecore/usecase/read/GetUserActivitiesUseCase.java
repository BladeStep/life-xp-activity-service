package com.bladestepapp.lifexpactivityservicecore.usecase.read;

import com.bladestepapp.lifexpactivityservicecore.model.UserActivityResponseModel;

import java.util.List;

public interface GetUserActivitiesUseCase {

    List<UserActivityResponseModel> find(GetUserActivityQuery query);
}
