package com.bladestepapp.lifexpactivityserviceapplication.service.write;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bladestepapp.lifexpactivityservicecore.domain.Activity;
import com.bladestepapp.lifexpactivityservicecore.domain.User;
import com.bladestepapp.lifexpactivityservicecore.domain.UserActivity;
import com.bladestepapp.lifexpactivityservicecore.domain.enums.ActivityCategory;
import com.bladestepapp.lifexpactivityservicecore.domain.enums.ActivityUnit;
import com.bladestepapp.lifexpactivityservicecore.exception.ActivityNotFoundException;
import com.bladestepapp.lifexpactivityservicecore.exception.UserNotFoundException;
import com.bladestepapp.lifexpactivityservicecore.gateway.GetUserPort;
import com.bladestepapp.lifexpactivityservicecore.persistence.GetActivityPort;
import com.bladestepapp.lifexpactivityservicecore.persistence.SaveUserActivityPort;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.CreateUserActivityCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class CreateUserActivityServiceTest {

    @Mock
    private SaveUserActivityPort saveUserActivityPort;

    @Mock
    private GetUserPort getUserPort;

    @Mock
    private GetActivityPort getActivityPort;

    @InjectMocks
    private CreateUserActivityService createUserActivityService;

    @Test
    void shouldCreateUserActivityAndReturnUUID() {
        //given
        UUID userId = UUID.randomUUID();
        UUID activityId = UUID.randomUUID();
        int customXp = 200;

        User user = User.create(userId, "TestUser", "test@gmail.com");
        Activity activity = Activity.create("Football", "Football game", ActivityCategory.SPORT, ActivityUnit.HOURS, 3);

        CreateUserActivityCommand command = new CreateUserActivityCommand(userId, activityId, customXp);

        UUID expectedUUID = UUID.randomUUID();
        UserActivity userActivity = UserActivity.create(userId, activityId, customXp);

        when(saveUserActivityPort.save(userActivity)).thenReturn(expectedUUID);

        when(getUserPort.find(userId)).thenReturn(Optional.of(user));
        when(getActivityPort.find(activityId)).thenReturn(Optional.of(activity));

        //when
        UUID result = createUserActivityService.execute(command);

        //then
        assertNotNull(result);
        assertEquals(expectedUUID, result);

        verify(saveUserActivityPort).save(userActivity);
    }

    @Test
    void shouldThrowUserNotFoundException_whenUserDoesNotExist(){
        UUID userId = UUID.randomUUID();
        UUID activityId = UUID.randomUUID();
        int customXp = 200;

        when(getUserPort.find(userId)).thenReturn(Optional.empty());

        CreateUserActivityCommand command = new CreateUserActivityCommand(userId, activityId, customXp);

        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> createUserActivityService.execute(command));

        assertEquals("User with id " + userId + " was not found", exception.getMessage());

        verify(getUserPort).find(userId);
        verify(saveUserActivityPort, never()).save(any());
    }

    @Test
    void shouldThrowActivityNotFoundException_whenActivityDoesNotExist(){
        UUID userId = UUID.randomUUID();
        UUID activityId = UUID.randomUUID();
        int customXp = 200;

        User user = User.create(userId, "TestUser", "test@gmail.com");

        when(getUserPort.find(userId)).thenReturn(Optional.of(user));
        when(getActivityPort.find(activityId)).thenReturn(Optional.empty());

        CreateUserActivityCommand command = new CreateUserActivityCommand(userId, activityId, customXp);

        ActivityNotFoundException exception = assertThrows(
                ActivityNotFoundException.class,
                () -> createUserActivityService.execute(command));

        assertEquals("Activity with id " + activityId + " was not found", exception.getMessage());

        verify(getUserPort).find(userId);
        verify(getActivityPort).find(activityId);
        verify(saveUserActivityPort, never()).save(any());
    }
}