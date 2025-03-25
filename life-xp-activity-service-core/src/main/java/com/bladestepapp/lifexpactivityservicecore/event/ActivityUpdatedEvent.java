package com.bladestepapp.lifexpactivityservicecore.event;

import lombok.Data;
import lombok.Value;

import java.util.UUID;

@Value
@Data
public class ActivityUpdatedEvent {
    UUID activityId;
    String status;

    public ActivityUpdatedEvent() {
        this.activityId = null;
        this.status = null;
    }

    public ActivityUpdatedEvent(UUID activityId, String status) {
        this.activityId = activityId;
        this.status = status;
    }
}