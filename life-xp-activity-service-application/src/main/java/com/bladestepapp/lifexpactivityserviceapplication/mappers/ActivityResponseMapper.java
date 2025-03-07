package com.bladestepapp.lifexpactivityserviceapplication.mappers;

import com.bladestepapp.lifexpactivityservicecore.domain.Activity;
import com.bladestepapp.lifexpactivityservicecore.models.ActivityResponseModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActivityResponseMapper {

    ActivityResponseModel map(Activity activity);
}