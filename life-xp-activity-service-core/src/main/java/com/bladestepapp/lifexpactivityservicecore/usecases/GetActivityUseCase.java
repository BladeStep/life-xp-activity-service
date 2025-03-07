package com.bladestepapp.lifexpactivityservicecore.usecases;

import com.bladestepapp.lifexpactivityservicecore.models.ActivityResponseModel;
import com.bladestepapp.lifexpactivityservicecore.queries.GetActivityQuery;

public interface GetActivityUseCase {

    ActivityResponseModel get(GetActivityQuery query);
}
