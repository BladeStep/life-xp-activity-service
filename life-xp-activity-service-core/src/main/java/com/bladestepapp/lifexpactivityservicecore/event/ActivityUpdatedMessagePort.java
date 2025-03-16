package com.bladestepapp.lifexpactivityservicecore.event;

import java.util.UUID;

public interface ActivityUpdatedMessagePort {

    void send(UUID activityId);
}