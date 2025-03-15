package com.bladestepapp.lifexpactivityserviceapplication.service.write;

import com.bladestepapp.lifexpactivityservicecore.domain.Activity;
import com.bladestepapp.lifexpactivityservicecore.persistence.SaveActivityPort;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.UpdateActivityCommand;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.UpdateActivityUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateActivityService implements UpdateActivityUseCase {

    private final SaveActivityPort saveActivityPort;

    @Override
    public void execute(UpdateActivityCommand command) {

        Activity activity = Activity.restore(
                command.getId(),
                command.getName(),
                command.getDescription(),
                command.getCategory(),
                command.getUnit(),
                command.getBaseXp()
        );
        saveActivityPort.save(activity);
    }
}
