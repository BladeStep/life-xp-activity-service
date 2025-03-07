package com.bladestepapp.lifexpactivityservicecore.usecases;

import com.bladestepapp.lifexpactivityservicecore.commands.CreateActivityCommand;

import java.util.UUID;

public interface CreateActivityUseCase {

    UUID execute(CreateActivityCommand command);
}
