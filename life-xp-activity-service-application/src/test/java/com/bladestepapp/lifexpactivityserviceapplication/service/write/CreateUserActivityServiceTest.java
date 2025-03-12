package com.bladestepapp.lifexpactivityserviceapplication.service.write;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bladestepapp.lifexpactivityservicecore.domain.UserActivity;
import com.bladestepapp.lifexpactivityservicecore.persistence.SaveUserActivityPort;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.CreateUserActivityCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class CreateUserActivityServiceTest {

    @Mock
    private SaveUserActivityPort saveUserActivityPort;

    @InjectMocks
    private CreateUserActivityService createUserActivityService;

    @Test
    void shouldCreateUserActivityAndReturnUUID() {
        //given
        UUID userId = UUID.randomUUID();
        UUID activityId = UUID.randomUUID();
        int customXp = 200;

        CreateUserActivityCommand command = new CreateUserActivityCommand(userId, activityId, customXp);

        UUID expectedUUID = UUID.randomUUID();
        UserActivity userActivity = UserActivity.create(userId, activityId, customXp);

        when(saveUserActivityPort.save(userActivity)).thenReturn(expectedUUID);

        //when
        UUID result = createUserActivityService.execute(command);

        //then
        assertNotNull(result);
        assertEquals(expectedUUID, result);

        verify(saveUserActivityPort).save(userActivity);
    }
}