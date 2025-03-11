package com.bladestepapp.lifexpactivityservicecore.usecase.write;

import java.util.UUID;

public interface CreateActivityUseCase {

    UUID execute(CreateActivityCommand command);
}
