package com.bladestepapp.lifexpactivityserviceapplication.service.write;

import com.bladestepapp.lifexpactivityservicecore.domain.UserActivity;
import com.bladestepapp.lifexpactivityservicecore.repository.SaveUserActivityPort;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.CreateUserActivityCommand;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.CreateUserActivityUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateUserActivityService implements CreateUserActivityUseCase {

    private final SaveUserActivityPort saveUserActivityPort;

    @Override
    public UUID execute(CreateUserActivityCommand command) {
        UserActivity userActivity = UserActivity.create(
                command.getUserId(),
                command.getActivityId(),
                command.getCustomXp()
        );
        return saveUserActivityPort.save(userActivity);
    }
}
