package com.bladestepapp.lifexpactivityservicecore.usecase.read;

import com.bladestepapp.lifexpactivityservicecore.model.ActivityResponseModel;

import java.util.List;

public interface GetAllActivitiesUseCase {

    List<ActivityResponseModel> get();
}
