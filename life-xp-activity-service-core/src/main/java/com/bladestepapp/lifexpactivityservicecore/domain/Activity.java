package com.bladestepapp.lifexpactivityservicecore.domain;

import com.bladestepapp.lifexpactivityservicecore.domain.enums.ActivityCategory;
import com.bladestepapp.lifexpactivityservicecore.domain.enums.ActivityUnit;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Activity {

    private String name;

    private String description;

    private ActivityCategory category;

    private ActivityUnit unit;

    private int baseXp;

    public static Activity create(
            String name,
            String description,
            ActivityCategory category,
            ActivityUnit unit,
            int baseXp
    ) {
        return new Activity(name,
                description,
                category,
                unit,
                baseXp
        );
    }
}