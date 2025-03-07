package com.bladestepapp.lifexpactivityserviceapplication.services;

import com.bladestepapp.lifexpactivityservicecore.commands.CreateActivityCommand;
import com.bladestepapp.lifexpactivityservicecore.domain.Activity;
import com.bladestepapp.lifexpactivityservicecore.ports.SaveActivityPort;
import com.bladestepapp.lifexpactivityservicecore.usecases.CreateActivityUseCase;
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
