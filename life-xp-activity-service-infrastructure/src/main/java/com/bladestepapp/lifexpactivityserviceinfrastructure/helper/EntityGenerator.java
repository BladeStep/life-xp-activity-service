package com.bladestepapp.lifexpactivityserviceinfrastructure.helper;

import com.bladestepapp.lifexpactivityserviceinfrastructure.entity.ActivityEntity;
import com.bladestepapp.lifexpactivityserviceinfrastructure.entity.UserActivityEntity;
import com.bladestepapp.lifexpactivityserviceinfrastructure.entity.enums.ActivityCategory;
import com.bladestepapp.lifexpactivityserviceinfrastructure.entity.enums.ActivityUnit;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class EntityGenerator {

    public static final String ACTIVITY_NAME = "FOOTBALL";
    public static final String ACTIVITY_DESCRIPTION = "Football game";

    public static ActivityEntity createEntity() {
        return getActivityEntity(ACTIVITY_NAME, ACTIVITY_DESCRIPTION);
    }

    public static ActivityEntity createEntity(String name, String description) {
        return getActivityEntity(name, description);
    }

    private static ActivityEntity getActivityEntity(String name, String description) {
        ActivityEntity activityEntity = new ActivityEntity();
        activityEntity.setId(UUID.randomUUID());
        activityEntity.setName(name);
        activityEntity.setDescription(description);
        activityEntity.setCategory(ActivityCategory.SPORT);
        activityEntity.setUnit(ActivityUnit.HOURS);
        activityEntity.setBaseXp(50);
        return activityEntity;
    }

    public static UserActivityEntity createUserActivityEntity(UUID userId, UUID activityId) {
        UserActivityEntity userActivityEntity = new UserActivityEntity();
        userActivityEntity.setId(UUID.randomUUID());
        userActivityEntity.setUserId(userId);
        userActivityEntity.setActivityId(activityId);
        return userActivityEntity;
    }
}
