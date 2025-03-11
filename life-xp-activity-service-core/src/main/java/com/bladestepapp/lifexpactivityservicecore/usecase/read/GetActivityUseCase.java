package com.bladestepapp.lifexpactivityservicecore.usecase.read;

import com.bladestepapp.lifexpactivityservicecore.model.ActivityResponseModel;

public interface GetActivityUseCase {

    ActivityResponseModel get(GetActivityQuery query);
}
