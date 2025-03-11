package com.bladestepapp.lifexpactivityservicecore.usecase.write;

import java.util.UUID;

public interface CreateUserActivityUseCase {

    UUID execute(CreateUserActivityCommand command);
}
