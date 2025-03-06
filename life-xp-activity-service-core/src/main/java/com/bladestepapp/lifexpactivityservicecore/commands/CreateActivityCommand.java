package com.bladestepapp.lifexpactivityservicecore.commands;

import com.bladestepapp.lifexpactivityservicecore.domain.enums.ActivityCategory;
import com.bladestepapp.lifexpactivityservicecore.domain.enums.ActivityUnit;
import lombok.Value;

@Value
public class CreateActivityCommand {

    String name;

    String description;

    ActivityCategory category;

    ActivityUnit unit;

    double baseXp;
}