package com.bladestepapp.lifexpactivityservicecore.model;

import com.bladestepapp.lifexpactivityservicecore.domain.enums.ActivityCategory;
import com.bladestepapp.lifexpactivityservicecore.domain.enums.ActivityUnit;
import lombok.Data;

@Data
public class ActivityResponseModel {

    private String name;

    private String description;

    private ActivityCategory category;

    private ActivityUnit unit;

    private double baseXp;
}
