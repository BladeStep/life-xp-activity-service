package com.bladestepapp.lifexpactivityservicecore.model;

import com.bladestepapp.lifexpactivityservicecore.domain.enums.ActivityCategory;
import com.bladestepapp.lifexpactivityservicecore.domain.enums.ActivityUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserActivityResponseModel {

    private UUID activityId;

    private String activityName;

    private ActivityCategory category;

    private ActivityUnit unit;

    private double baseXp;

    private Integer customXp;
}
