package com.bladestepapp.lifexpactivityserviceapplication.service.write;

import com.bladestepapp.lifexpactivityservicecore.domain.Activity;
import com.bladestepapp.lifexpactivityservicecore.event.ActivityUpdatedMessagePort;
import com.bladestepapp.lifexpactivityservicecore.persistence.SaveActivityPort;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.UpdateActivityCommand;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.UpdateActivityUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateActivityService implements UpdateActivityUseCase {

    private final SaveActivityPort saveActivityPort;

    private final ActivityUpdatedMessagePort activityUpdatedMessagePort;

    @Override
    public void execute(UpdateActivityCommand command) {
        UUID activityId = command.getId();

        Activity activity = Activity.restore(
                activityId,
                command.getName(),
                command.getDescription(),
                command.getCategory(),
                command.getUnit(),
                command.getBaseXp()
        );
        saveActivityPort.save(activity);
        activityUpdatedMessagePort.send(activityId);
    }
}
