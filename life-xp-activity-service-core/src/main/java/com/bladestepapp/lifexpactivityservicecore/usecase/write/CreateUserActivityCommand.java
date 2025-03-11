package com.bladestepapp.lifexpactivityservicecore.usecase.write;

import lombok.Value;

import java.util.UUID;

@Value
public class CreateUserActivityCommand {

    UUID userId;

    UUID activityId;

    Integer customXp;
}
