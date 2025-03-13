package com.bladestepapp.lifexpactivityserviceapplication.service.write;

import com.bladestepapp.lifexpactivityservicecore.domain.UserActivity;
import com.bladestepapp.lifexpactivityservicecore.exception.UserNotFoundException;
import com.bladestepapp.lifexpactivityservicecore.gateway.GetUserPort;
import com.bladestepapp.lifexpactivityservicecore.persistence.SaveUserActivityPort;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.CreateUserActivityCommand;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.CreateUserActivityUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateUserActivityService implements CreateUserActivityUseCase {

    private final SaveUserActivityPort saveUserActivityPort;

    private final GetUserPort getUserPort;

    @Override
    public UUID execute(CreateUserActivityCommand command) {

        getUserPort.get(command.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User with id " + command.getUserId() + " was not found"));

        UserActivity userActivity = UserActivity.create(
                command.getUserId(),
                command.getActivityId(),
                command.getCustomXp()
        );
        return saveUserActivityPort.save(userActivity);
    }
}
