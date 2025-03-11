package com.bladestepapp.lifexpactivityservicecore.model;

import com.bladestepapp.lifexpactivityservicecore.domain.enums.ActivityCategory;
import com.bladestepapp.lifexpactivityservicecore.domain.enums.ActivityUnit;
import lombok.Data;

import java.util.UUID;

@Data
public class UserActivityResponseModel {

    private UUID activityId;

    private String activityName;

    private ActivityCategory category;

    private ActivityUnit unit;

    private double baseXp;

    private Integer customXp;
}
