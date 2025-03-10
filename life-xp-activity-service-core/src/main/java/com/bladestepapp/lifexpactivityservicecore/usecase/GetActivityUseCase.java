package com.bladestepapp.lifexpactivityservicecore.usecase;

import com.bladestepapp.lifexpactivityservicecore.model.ActivityResponseModel;
import com.bladestepapp.lifexpactivityservicecore.query.GetActivityQuery;

public interface GetActivityUseCase {

    ActivityResponseModel get(GetActivityQuery query);
}
