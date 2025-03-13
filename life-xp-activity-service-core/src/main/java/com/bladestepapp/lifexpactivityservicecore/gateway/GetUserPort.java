package com.bladestepapp.lifexpactivityservicecore.gateway;

import com.bladestepapp.lifexpactivityservicecore.domain.User;

import java.util.Optional;
import java.util.UUID;

public interface GetUserPort {

    Optional<User> get(UUID id);
}
