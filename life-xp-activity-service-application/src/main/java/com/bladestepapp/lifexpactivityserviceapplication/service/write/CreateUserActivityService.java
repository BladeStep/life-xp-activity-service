package com.bladestepapp.lifexpactivityserviceapplication.service.write;

import com.bladestepapp.lifexpactivityservicecore.domain.UserActivity;
import com.bladestepapp.lifexpactivityservicecore.exception.ActivityNotFoundException;
import com.bladestepapp.lifexpactivityservicecore.exception.UserNotFoundException;
import com.bladestepapp.lifexpactivityservicecore.gateway.GetUserPort;
import com.bladestepapp.lifexpactivityservicecore.persistence.GetActivityPort;
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

    private final GetActivityPort getActivityPort;

    @Override
    public UUID execute(CreateUserActivityCommand command) {
        validateUserExists(command.getUserId());
        validateActivityExists(command.getActivityId());

        UserActivity userActivity = UserActivity.create(command.getUserId(), command.getActivityId(), command.getCustomXp());
        return saveUserActivityPort.save(userActivity);
    }

    private void validateUserExists(UUID userId) {
        if (getUserPort.find(userId).isEmpty()) {
            throw new UserNotFoundException("User with id " + userId + " was not found");
        }
    }

    private void validateActivityExists(UUID activityId) {
        if (getActivityPort.find(activityId).isEmpty()) {
            throw new ActivityNotFoundException("Activity with id " + activityId + " was not found");
        }
    }
}
