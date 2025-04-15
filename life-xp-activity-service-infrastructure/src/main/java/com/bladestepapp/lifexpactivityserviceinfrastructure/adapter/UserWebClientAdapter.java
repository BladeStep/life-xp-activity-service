package com.bladestepapp.lifexpactivityserviceinfrastructure.adapter;

import com.bladestepapp.lifexpactivityservicecore.domain.User;
import com.bladestepapp.lifexpactivityservicecore.gateway.GetUserPort;
import com.bladestepapp.lifexpactivityserviceinfrastructure.gateway.UserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserWebClientAdapter implements GetUserPort {

    private final UserGateway userGateway;

    @Override
    public Optional<User> find(UUID id) {
        return userGateway.find(id)
                .map(userModelResponse -> User.create(
                        userModelResponse.getId(),
                        userModelResponse.getName(),
                        userModelResponse.getEmail()
                ));
    }
}