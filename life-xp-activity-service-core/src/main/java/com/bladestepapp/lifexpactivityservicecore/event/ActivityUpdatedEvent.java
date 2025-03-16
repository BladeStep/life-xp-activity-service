package com.bladestepapp.lifexpactivityservicecore.event;

import lombok.Value;

import java.util.UUID;

@Value
public class ActivityUpdatedEvent {
    UUID activityId;
    String status;
}