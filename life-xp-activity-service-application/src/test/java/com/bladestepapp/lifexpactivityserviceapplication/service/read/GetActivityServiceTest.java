package com.bladestepapp.lifexpactivityserviceapplication.service.read;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bladestepapp.lifexpactivityserviceapplication.mapper.ActivityResponseMapper;
import com.bladestepapp.lifexpactivityserviceapplication.mapper.ActivityResponseMapperImpl;
import com.bladestepapp.lifexpactivityservicecore.domain.Activity;
import com.bladestepapp.lifexpactivityservicecore.domain.enums.ActivityCategory;
import com.bladestepapp.lifexpactivityservicecore.domain.enums.ActivityUnit;
import com.bladestepapp.lifexpactivityservicecore.exception.ActivityNotFoundException;
import com.bladestepapp.lifexpactivityservicecore.model.ActivityResponseModel;
import com.bladestepapp.lifexpactivityservicecore.persistence.GetActivityPort;
import com.bladestepapp.lifexpactivityservicecore.usecase.read.GetActivityQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class GetActivityServiceTest {

    @Mock
    private GetActivityPort getActivityPort;

    @Spy
    private ActivityResponseMapper mapper = new ActivityResponseMapperImpl();

    @InjectMocks
    private GetActivityService getActivityService;

    @Test
    void shouldReturnActivityResponseModel_whenActivityExists() {
        //given
        UUID activityId = UUID.randomUUID();
        GetActivityQuery query = new GetActivityQuery(activityId);

        Activity activity = Activity.create("Football",
                "Played football for 2 hours",
                ActivityCategory.SPORT,
                ActivityUnit.HOURS,
                200);

        activity.setId(activityId);

        ActivityResponseModel expectedResponse = new ActivityResponseModel(
                activityId,
                activity.getName(),
                activity.getDescription(),
                activity.getCategory(),
                activity.getUnit(),
                activity.getBaseXp()
        );

        when(getActivityPort.find(activityId)).thenReturn(Optional.of(activity));

        //when
        ActivityResponseModel result = getActivityService.get(query);

        //then
        assertNotNull(result);
        assertEquals(expectedResponse, result);

        verify(getActivityPort).find(activityId);
    }

    @Test
    void get_shouldThrowActivityNotFoundException_whenActivityDoesNotExist() {
        //given
        UUID activityId = UUID.randomUUID();
        GetActivityQuery query = new GetActivityQuery(activityId);

        when(getActivityPort.find(activityId)).thenReturn(Optional.empty());

        //when,then
        ActivityNotFoundException exception = assertThrows(
                ActivityNotFoundException.class,
                () -> getActivityService.get(query));

        assertEquals("Activity with id " + activityId + " was not found", exception.getMessage());

        verify(getActivityPort).find(activityId);
        verify(mapper, never()).map(any(Activity.class));
    }
}