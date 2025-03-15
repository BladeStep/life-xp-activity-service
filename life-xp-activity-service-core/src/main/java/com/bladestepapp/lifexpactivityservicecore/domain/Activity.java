package com.bladestepapp.lifexpactivityservicecore.domain;

import com.bladestepapp.lifexpactivityservicecore.domain.enums.ActivityCategory;
import com.bladestepapp.lifexpactivityservicecore.domain.enums.ActivityUnit;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
public class Activity {

    private UUID id;
    private String name;
    private String description;
    private ActivityCategory category;
    private ActivityUnit unit;
    private int baseXp;

    private Activity(UUID id, String name, String description, ActivityCategory category, ActivityUnit unit, int baseXp) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.unit = unit;
        this.baseXp = baseXp;
    }

    public static Activity create(String name, String description, ActivityCategory category, ActivityUnit unit, int baseXp) {
        return new Activity(null, name, description, category, unit, baseXp);
    }

    public static Activity restore(UUID id, String name, String description, ActivityCategory category, ActivityUnit unit, int baseXp) {
        return new Activity(id, name, description, category, unit, baseXp);
    }
}