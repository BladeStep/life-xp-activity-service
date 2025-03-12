package com.bladestepapp.lifexpactivityserviceapplication.service.read;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bladestepapp.lifexpactivityserviceapplication.mapper.UserActivityResponseMapper;
import com.bladestepapp.lifexpactivityserviceapplication.mapper.UserActivityResponseMapperImpl;
import com.bladestepapp.lifexpactivityservicecore.domain.Activity;
import com.bladestepapp.lifexpactivityservicecore.domain.UserActivity;
import com.bladestepapp.lifexpactivityservicecore.domain.enums.ActivityCategory;
import com.bladestepapp.lifexpactivityservicecore.domain.enums.ActivityUnit;
import com.bladestepapp.lifexpactivityservicecore.exception.ActivityNotFoundException;
import com.bladestepapp.lifexpactivityservicecore.model.UserActivityResponseModel;
import com.bladestepapp.lifexpactivityservicecore.persistence.GetActivityPort;
import com.bladestepapp.lifexpactivityservicecore.persistence.GetUserActivitiesPort;
import com.bladestepapp.lifexpactivityservicecore.usecase.read.GetUserActivityQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class GetUserActivitiesServiceTest {

    @Mock
    private GetUserActivitiesPort getUserActivitiesPort;

    @Mock
    private GetActivityPort getActivityPort;

    @Spy
    private UserActivityResponseMapper mapper = new UserActivityResponseMapperImpl();

    @InjectMocks
    private GetUserActivitiesService getUserActivitiesService;

    @Test
    void find_shouldReturnListOfUserActivityResponseModels_whenActivitiesExist() {
        //given
        UUID userId = UUID.randomUUID();
        UUID firstActivityId = UUID.randomUUID();
        UUID secondActivityId = UUID.randomUUID();
        Integer firstCustomXp = 110;
        Integer secondCustomXp = 120;

        UserActivity firstUserActivity = UserActivity.create(userId, firstActivityId, firstCustomXp);
        UserActivity secondUserActivity = UserActivity.create(userId, secondActivityId, secondCustomXp);

        Activity firstActivity = Activity.create("Football", "Football", ActivityCategory.SPORT, ActivityUnit.HOURS, 10);
        Activity secondActivity = Activity.create("Reading", "Read a book", ActivityCategory.EDUCATION, ActivityUnit.SESSIONS, 2);

        UserActivityResponseModel firstExpectedResponse = new UserActivityResponseModel(
                firstActivityId,
                firstActivity.getName(),
                firstActivity.getCategory(),
                firstActivity.getUnit(),
                firstActivity.getBaseXp(),
                firstCustomXp);

        UserActivityResponseModel secondExpectedResponse = new UserActivityResponseModel(
                secondActivityId,
                secondActivity.getName(),
                secondActivity.getCategory(),
                secondActivity.getUnit(),
                secondActivity.getBaseXp(),
                secondCustomXp);

        when(getUserActivitiesPort.findByUserId(userId)).thenReturn(Arrays.asList(firstUserActivity, secondUserActivity));
        when(getActivityPort.find(firstActivityId)).thenReturn(Optional.of(firstActivity));
        when(getActivityPort.find(secondActivityId)).thenReturn(Optional.of(secondActivity));

        //when
        List<UserActivityResponseModel> result = getUserActivitiesService.find(new GetUserActivityQuery(userId));

        // Проверка результата
        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(firstExpectedResponse, result.get(0));
        assertEquals(secondExpectedResponse, result.get(1));

        // Проверка вызовов
        verify(getUserActivitiesPort).findByUserId(userId);
        verify(getActivityPort).find(firstActivityId);
        verify(getActivityPort).find(secondActivityId);
    }

    @Test
    void find_shouldThrowActivityNotFoundException_whenActivityDoesNotExist() {
        //given
        UUID userId = UUID.randomUUID();
        UUID activityId = UUID.randomUUID();

        UserActivity userActivity = UserActivity.create(userId, activityId, 10);

        when(getUserActivitiesPort.findByUserId(userId)).thenReturn(Collections.singletonList(userActivity));
        when(getActivityPort.find(activityId)).thenReturn(Optional.empty());

        // Выполнение метода и проверка исключения
        ActivityNotFoundException exception = assertThrows(
                ActivityNotFoundException.class,
                () -> getUserActivitiesService.find(new GetUserActivityQuery(userId)));

        // Проверка сообщения исключения
        assertEquals("Activity with id " + activityId + " was not found", exception.getMessage());

        verify(getUserActivitiesPort).findByUserId(userId);
        verify(getActivityPort).find(activityId);
        verify(mapper, never()).map(any(), any());
    }
}