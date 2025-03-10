package com.bladestepapp.lifexpactivityserviceinfrastructure.helpers;

import com.bladestepapp.lifexpactivityserviceinfrastructure.entities.ActivityEntity;
import com.bladestepapp.lifexpactivityserviceinfrastructure.entities.enums.ActivityCategory;
import com.bladestepapp.lifexpactivityserviceinfrastructure.entities.enums.ActivityUnit;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class EntityGenerator {

    public static final String ACTIVITY_NAME = "FOOTBALL";
    public static final String ACTIVITY_DESCRIPTION = "Football game";

    public static ActivityEntity createEntity() {
        ActivityEntity activityEntity = new ActivityEntity();
        activityEntity.setId(UUID.randomUUID());
        activityEntity.setName(ACTIVITY_NAME);
        activityEntity.setDescription(ACTIVITY_DESCRIPTION);
        activityEntity.setCategory(ActivityCategory.SPORT);
        activityEntity.setUnit(ActivityUnit.HOURS);
        activityEntity.setBaseXp(50);
        return activityEntity;
    }

    public static ActivityEntity createEntityWithoutId() {
        ActivityEntity activityEntity = new ActivityEntity();
        activityEntity.setName(ACTIVITY_NAME);
        activityEntity.setDescription(ACTIVITY_DESCRIPTION);
        activityEntity.setCategory(ActivityCategory.SPORT);
        activityEntity.setUnit(ActivityUnit.HOURS);
        activityEntity.setBaseXp(50);
        return activityEntity;
    }
}
