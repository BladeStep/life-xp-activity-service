package com.bladestepapp.lifexpactivityservicecore.usecase.write;

import lombok.Value;

import java.util.UUID;

@Value
public class DeleteActivityCommand {

    UUID id;
}
