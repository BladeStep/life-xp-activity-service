package com.bladestepapp.lifexpactivityserviceinfrastructure.entity;

import com.bladestepapp.lifexpactivityserviceinfrastructure.entity.enums.ActivityCategory;
import com.bladestepapp.lifexpactivityserviceinfrastructure.entity.enums.ActivityUnit;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "activities")
@EqualsAndHashCode
public class ActivityEntity {

    @Id
    private UUID id;
    private String name;
    private String description;
    private ActivityCategory category;
    private ActivityUnit unit;
    private int baseXp;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ActivityCategory getCategory() {
        return category;
    }

    public void setCategory(ActivityCategory category) {
        this.category = category;
    }

    public ActivityUnit getUnit() {
        return unit;
    }

    public void setUnit(ActivityUnit unit) {
        this.unit = unit;
    }

    public int getBaseXp() {
        return baseXp;
    }

    public void setBaseXp(int baseXp) {
        this.baseXp = baseXp;
    }
}