package com.bladestepapp.lifexpactivityservicecore.usecase.write;

import com.bladestepapp.lifexpactivityservicecore.domain.enums.ActivityCategory;
import com.bladestepapp.lifexpactivityservicecore.domain.enums.ActivityUnit;

public final class CreateActivityCommand {

    private final String name;

    private final String description;

    private final ActivityCategory category;

    private final ActivityUnit unit;

    private final int baseXp;

    public CreateActivityCommand(String name, String description, ActivityCategory category, ActivityUnit unit, int baseXp) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.unit = unit;
        this.baseXp = baseXp;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public ActivityCategory getCategory() {
        return this.category;
    }

    public ActivityUnit getUnit() {
        return this.unit;
    }

    public int getBaseXp() {
        return this.baseXp;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof CreateActivityCommand))
            return false;
        final CreateActivityCommand other = (CreateActivityCommand) o;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description))
            return false;
        final Object this$category = this.getCategory();
        final Object other$category = other.getCategory();
        if (this$category == null ? other$category != null : !this$category.equals(other$category)) return false;
        final Object this$unit = this.getUnit();
        final Object other$unit = other.getUnit();
        if (this$unit == null ? other$unit != null : !this$unit.equals(other$unit)) return false;
        if (this.getBaseXp() != other.getBaseXp()) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final Object $category = this.getCategory();
        result = result * PRIME + ($category == null ? 43 : $category.hashCode());
        final Object $unit = this.getUnit();
        result = result * PRIME + ($unit == null ? 43 : $unit.hashCode());
        result = result * PRIME + this.getBaseXp();
        return result;
    }

    public String toString() {
        return "CreateActivityCommand(name=" + this.getName() + ", description=" + this.getDescription() + ", category=" + this.getCategory() + ", unit=" + this.getUnit() + ", baseXp=" + this.getBaseXp() + ")";
    }
}