package com.bladestepapp.lifexpactivityserviceapplication.service.write;

import static org.mockito.Mockito.verify;

import com.bladestepapp.lifexpactivityservicecore.domain.Activity;
import com.bladestepapp.lifexpactivityservicecore.domain.enums.ActivityCategory;
import com.bladestepapp.lifexpactivityservicecore.domain.enums.ActivityUnit;
import com.bladestepapp.lifexpactivityservicecore.persistence.SaveActivityPort;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.UpdateActivityCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class UpdateActivityServiceTest {

    @Mock
    private SaveActivityPort saveActivityPort;

    @InjectMocks
    private UpdateActivityService updateActivityService;

    @Test
    void shouldCreateActivityAndReturnUUID() {
        //given
        UUID activityId = UUID.randomUUID();
        String name = "Football";
        String description = "Played football for 2 hours";
        ActivityCategory category = ActivityCategory.SPORT;
        ActivityUnit unit = ActivityUnit.HOURS;
        int baseXp = 200;

        UpdateActivityCommand command = new UpdateActivityCommand(activityId, name, description, category, unit, baseXp);

        Activity activity = Activity.restore(activityId, name, description, category, unit, baseXp);

        //when
        updateActivityService.execute(command);

        //then
        verify(saveActivityPort).save(activity);
    }
}