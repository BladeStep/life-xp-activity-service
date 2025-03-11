package com.bladestepapp.lifexpactivityservicecore.repository;

import com.bladestepapp.lifexpactivityservicecore.domain.UserActivity;

import java.util.UUID;

public interface SaveUserActivityPort {

    UUID save(UserActivity userActivity);
}
