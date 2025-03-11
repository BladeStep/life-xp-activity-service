package com.bladestepapp.lifexpactivityservicecore.repository;

import com.bladestepapp.lifexpactivityservicecore.domain.Activity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GetActivityPort {

    Optional<Activity> get(UUID id);

    List<Activity> getAll();
}