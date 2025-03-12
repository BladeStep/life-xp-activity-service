package com.bladestepapp.lifexpactivityserviceapplication.service.write;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bladestepapp.lifexpactivityservicecore.domain.Activity;
import com.bladestepapp.lifexpactivityservicecore.domain.enums.ActivityCategory;
import com.bladestepapp.lifexpactivityservicecore.domain.enums.ActivityUnit;
import com.bladestepapp.lifexpactivityservicecore.persistence.SaveActivityPort;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.CreateActivityCommand;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class CreateActivityServiceTest {

    @Mock
    private SaveActivityPort saveActivityPort;

    @InjectMocks
    private CreateActivityService createActivityService;

    @Test
    void shouldCreateActivityAndReturnUUID() {
        //given
        String name = "Football";
        String description = "Played football for 2 hours";
        ActivityCategory category = ActivityCategory.SPORT;
        ActivityUnit unit = ActivityUnit.HOURS;
        int baseXp = 200;

        CreateActivityCommand command = new CreateActivityCommand(name, description, category, unit, baseXp);

        UUID expectedUUID = UUID.randomUUID();
        Activity activity = Activity.create(name, description, category, unit, baseXp);

        when(saveActivityPort.save(activity)).thenReturn(expectedUUID);

        //when
        UUID result = createActivityService.execute(command);

        assertNotNull(result);
        assertEquals(expectedUUID, result);

        verify(saveActivityPort).save(activity);
    }
}