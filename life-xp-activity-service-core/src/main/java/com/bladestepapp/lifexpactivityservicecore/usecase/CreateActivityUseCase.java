package com.bladestepapp.lifexpactivityservicecore.usecase;

import com.bladestepapp.lifexpactivityservicecore.command.CreateActivityCommand;

import java.util.UUID;

public interface CreateActivityUseCase {

    UUID execute(CreateActivityCommand command);
}
