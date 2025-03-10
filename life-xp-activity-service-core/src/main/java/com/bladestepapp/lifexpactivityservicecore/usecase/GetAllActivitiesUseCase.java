package com.bladestepapp.lifexpactivityservicecore.usecase;

import com.bladestepapp.lifexpactivityservicecore.model.ActivityResponseModel;

import java.util.List;

public interface GetAllActivitiesUseCase {

    List<ActivityResponseModel> get();
}
