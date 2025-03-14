package com.bladestepapp.lifexpactivityserviceapplication.service.write;

import com.bladestepapp.lifexpactivityservicecore.exception.ActivityNotFoundException;
import com.bladestepapp.lifexpactivityservicecore.persistence.DeleteActivityPort;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.DeleteActivityCommand;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.DeleteActivityUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteActivityService implements DeleteActivityUseCase {

    private final DeleteActivityPort deleteActivityPort;

    @Override
    public void execute(DeleteActivityCommand command) {
        if (deleteActivityPort.delete(command.getId()) == 0) {
            throw new ActivityNotFoundException("Activity with id " + command.getId() + " was not found");
        }
    }
}
