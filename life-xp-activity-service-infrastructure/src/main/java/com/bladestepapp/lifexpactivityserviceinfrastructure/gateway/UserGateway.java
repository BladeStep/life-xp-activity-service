package com.bladestepapp.lifexpactivityserviceinfrastructure.gateway;

import com.bladestepapp.api.UserApiClient;
import com.bladestepapp.lifexpactivityserviceinfrastructure.gateway.model.UserModelResponse;
import com.bladestepapp.model.UserResponseDto;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserGateway {

    private final UserApiClient userApiClient;

    public Optional<UserModelResponse> get(UUID id) {
        try {
            ResponseEntity<UserResponseDto> response = userApiClient.getUserById(id);

            UserResponseDto userDto = response.getBody();

            return Optional.of(new UserModelResponse(userDto.getId(), userDto.getName(), userDto.getEmail()));
        } catch (FeignException.NotFound e) {
            return Optional.empty();
        } catch (FeignException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch user due to Feign error", e);
        }
    }
}
