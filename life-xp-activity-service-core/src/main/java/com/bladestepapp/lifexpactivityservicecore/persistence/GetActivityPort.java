package com.bladestepapp.lifexpactivityservicecore.persistence;

import com.bladestepapp.lifexpactivityservicecore.domain.Activity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GetActivityPort {

    Optional<Activity> find(UUID id);

    List<Activity> findAll();
}