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
public class ActivityResponseModel {

    private UUID id;

    private String name;

    private String description;

    private ActivityCategory category;

    private ActivityUnit unit;

    private double baseXp;
}
