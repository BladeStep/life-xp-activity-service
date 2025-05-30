package com.bladestepapp.lifexpactivityservicecore.persistence;

import com.bladestepapp.lifexpactivityservicecore.domain.UserActivity;

import java.util.List;
import java.util.UUID;

public interface GetUserActivitiesPort {

    List<UserActivity> findByUserId(UUID userId);
}
