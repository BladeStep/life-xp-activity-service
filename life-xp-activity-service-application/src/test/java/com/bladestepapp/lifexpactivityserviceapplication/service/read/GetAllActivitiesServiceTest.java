package com.bladestepapp.lifexpactivityserviceapplication.service.read;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bladestepapp.lifexpactivityserviceapplication.mapper.ActivityResponseMapper;
import com.bladestepapp.lifexpactivityserviceapplication.mapper.ActivityResponseMapperImpl;
import com.bladestepapp.lifexpactivityservicecore.domain.Activity;
import com.bladestepapp.lifexpactivityservicecore.domain.enums.ActivityCategory;
import com.bladestepapp.lifexpactivityservicecore.domain.enums.ActivityUnit;
import com.bladestepapp.lifexpactivityservicecore.model.ActivityResponseModel;
import com.bladestepapp.lifexpactivityservicecore.persistence.GetActivityPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class GetAllActivitiesServiceTest {

    @Mock
    private GetActivityPort getActivityPort;

    @Spy
    private ActivityResponseMapper mapper = new ActivityResponseMapperImpl();

    @InjectMocks
    private GetAllActivitiesService getAllActivitiesService;

    @Test
    void find_shouldReturnListOfActivityResponseModels_whenActivitiesExist() {
        //given
        Activity firstActivity = Activity.create("Football",
                "Played football for 2 hours",
                ActivityCategory.SPORT,
                ActivityUnit.HOURS,
                200);

        Activity secondActivity = Activity.create(      "Reading",
                "Read a book for 1 hour",
                ActivityCategory.EDUCATION,
                ActivityUnit.SESSIONS,
                100);


        List<Activity> activityList = Arrays.asList(firstActivity, secondActivity);

        ActivityResponseModel firstActivityExpectedResponse = new ActivityResponseModel(
                firstActivity.getName(),
                firstActivity.getDescription(),
                firstActivity.getCategory(),
                firstActivity.getUnit(),
                firstActivity.getBaseXp()
        );

        ActivityResponseModel secondActivityExpectedResponse = new ActivityResponseModel(
                secondActivity.getName(),
                secondActivity.getDescription(),
                secondActivity.getCategory(),
                secondActivity.getUnit(),
                secondActivity.getBaseXp()
        );

        List<ActivityResponseModel> expectedResponses = Arrays.asList(firstActivityExpectedResponse, secondActivityExpectedResponse);

        when(getActivityPort.findAll()).thenReturn(activityList);

        //when
        List<ActivityResponseModel> result = getAllActivitiesService.find();

        //then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedResponses, result);

        verify(getActivityPort).findAll();
        verify(mapper).map(activityList);
    }

    @Test
    void find_shouldReturnEmptyList_whenNoActivitiesExist() {
        //given
        List<Activity> emptyActivityList = Collections.emptyList();

        when(getActivityPort.findAll()).thenReturn(emptyActivityList);

        //when
        List<ActivityResponseModel> result = getAllActivitiesService.find();

        //then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(getActivityPort).findAll();
        verify(mapper).map(emptyActivityList);
    }
}