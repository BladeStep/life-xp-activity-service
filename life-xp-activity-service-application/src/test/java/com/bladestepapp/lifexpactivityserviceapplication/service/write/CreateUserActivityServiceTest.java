package com.bladestepapp.lifexpactivityserviceapplication.service.write;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bladestepapp.lifexpactivityservicecore.domain.User;
import com.bladestepapp.lifexpactivityservicecore.domain.UserActivity;
import com.bladestepapp.lifexpactivityservicecore.exception.UserNotFoundException;
import com.bladestepapp.lifexpactivityservicecore.gateway.GetUserPort;
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

    @InjectMocks
    private CreateUserActivityService createUserActivityService;

    @Test
    void shouldCreateUserActivityAndReturnUUID() {
        //given
        UUID userId = UUID.randomUUID();
        UUID activityId = UUID.randomUUID();
        int customXp = 200;

        User user = User.create(userId, "TestUser", "test@gmail.com");

        CreateUserActivityCommand command = new CreateUserActivityCommand(userId, activityId, customXp);

        UUID expectedUUID = UUID.randomUUID();
        UserActivity userActivity = UserActivity.create(userId, activityId, customXp);

        when(saveUserActivityPort.save(userActivity)).thenReturn(expectedUUID);

        when(getUserPort.get(userId)).thenReturn(Optional.of(user));

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

        when(getUserPort.get(userId)).thenReturn(Optional.empty());

        CreateUserActivityCommand command = new CreateUserActivityCommand(userId, activityId, customXp);

        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> createUserActivityService.execute(command));

        assertEquals("User with id " + userId + " was not found", exception.getMessage());

        verify(getUserPort).get(userId);
        verify(saveUserActivityPort, never()).save(any());
    }
}