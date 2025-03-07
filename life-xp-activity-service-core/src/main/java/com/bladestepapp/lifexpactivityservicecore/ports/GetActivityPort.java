package com.bladestepapp.lifexpactivityservicecore.ports;

import com.bladestepapp.lifexpactivityservicecore.domain.Activity;

import java.util.Optional;
import java.util.UUID;

public interface GetActivityPort {

    Optional<Activity> get(UUID id);
}