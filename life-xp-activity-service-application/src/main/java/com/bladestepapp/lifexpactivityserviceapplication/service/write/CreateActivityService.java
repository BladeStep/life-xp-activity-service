package com.bladestepapp.lifexpactivityserviceapplication.service.write;

import com.bladestepapp.lifexpactivityservicecore.usecase.write.CreateActivityCommand;
import com.bladestepapp.lifexpactivityservicecore.domain.Activity;
import com.bladestepapp.lifexpactivityservicecore.persistence.SaveActivityPort;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.CreateActivityUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateActivityService implements CreateActivityUseCase {

    private final SaveActivityPort saveActivityPort;

    @Override
    public UUID execute(CreateActivityCommand command) {
        Activity activity = Activity.create(
                command.getName(),
                command.getDescription(),
                command.getCategory(),
                command.getUnit(),
                command.getBaseXp()
        );
        return saveActivityPort.save(activity);
    }
}
