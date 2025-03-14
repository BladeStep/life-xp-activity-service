package com.bladestepapp.lifexpactivityserviceapplication.service.write;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bladestepapp.lifexpactivityservicecore.exception.ActivityNotFoundException;
import com.bladestepapp.lifexpactivityservicecore.persistence.DeleteActivityPort;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.DeleteActivityCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class DeleteActivityServiceTest {

    @Mock
    private DeleteActivityPort deleteActivityPort;

    @InjectMocks
    private DeleteActivityService deleteActivityService;

    @Test
    void shouldDeleteActivity_whenActivityExists() {
        //given
        UUID activityId = UUID.randomUUID();
        DeleteActivityCommand command = new DeleteActivityCommand(activityId);

        when(deleteActivityPort.delete(activityId)).thenReturn(1L);

        //when
        deleteActivityService.execute(command);

        //then
        verify(deleteActivityPort).delete(activityId);
    }

    @Test
    void shouldThrowException_whenActivityDoesNotExist() {
        //given
        UUID activityId = UUID.randomUUID();
        DeleteActivityCommand command = new DeleteActivityCommand(activityId);

        when(deleteActivityPort.delete(activityId)).thenReturn(0L);

        //when,then
        ActivityNotFoundException exception = assertThrows(ActivityNotFoundException.class, () -> {
            deleteActivityService.execute(command);
        });

        // Проверяем сообщение исключения
        assertThat(exception.getMessage()).isEqualTo("Activity with id " + activityId + " was not found");

        verify(deleteActivityPort).delete(activityId);
    }
}