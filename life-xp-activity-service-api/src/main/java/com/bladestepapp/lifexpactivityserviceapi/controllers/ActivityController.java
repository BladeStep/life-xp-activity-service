package com.bladestepapp.lifexpactivityserviceapi.controllers;

import com.bladestepapp.api.ActivitiesApi;
import com.bladestepapp.model.Activity;
import com.bladestepapp.model.ActivityInput;
import com.bladestepapp.model.MultiplierInput;
import com.bladestepapp.model.MultiplierResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ActivityController implements ActivitiesApi {

    @Override
    public ResponseEntity<Activity> createActivity(ActivityInput activityInput) {
        return ActivitiesApi.super.createActivity(activityInput);
    }

    @Override
    public ResponseEntity<Void> deleteActivity(UUID id) {
        return ActivitiesApi.super.deleteActivity(id);
    }

    @Override
    public ResponseEntity<List<Activity>> getActivities() {
        return ActivitiesApi.super.getActivities();
    }

    @Override
    public ResponseEntity<Activity> getActivityById(UUID id) {
        return ActivitiesApi.super.getActivityById(id);
    }

    @Override
    public ResponseEntity<MultiplierResponse> getActivityMultiplier(UUID id, UUID userId) {
        return ActivitiesApi.super.getActivityMultiplier(id, userId);
    }

    @Override
    public ResponseEntity<List<Activity>> getUserActivities(UUID userId) {
        return ActivitiesApi.super.getUserActivities(userId);
    }

    @Override
    public ResponseEntity<Void> setActivityMultiplier(UUID id, MultiplierInput multiplierInput) {
        return ActivitiesApi.super.setActivityMultiplier(id, multiplierInput);
    }

    @Override
    public ResponseEntity<Activity> updateActivity(UUID id, ActivityInput activityInput) {
        return ActivitiesApi.super.updateActivity(id, activityInput);
    }
}
