package com.bladestepapp.lifexpactivityserviceapi.mappers;

import com.bladestepapp.lifexpactivityservicecore.commands.CreateActivityCommand;
import com.bladestepapp.model.CreateActivityRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActivityMapper {

    CreateActivityCommand map(CreateActivityRequest createActivityRequest);
}