package com.bladestepapp.lifexpactivityserviceapi.controller;

import com.bladestepapp.api.UserActivityCommandApi;
import com.bladestepapp.lifexpactivityserviceapi.mapper.UserActivityMapper;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.CreateUserActivityCommand;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.CreateUserActivityUseCase;
import com.bladestepapp.model.CreateUserActivityRequestDto;
import com.bladestepapp.model.CreateUserActivityResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserActivityCommandController implements UserActivityCommandApi {

    private final CreateUserActivityUseCase createUserActivityUseCase;

    private final UserActivityMapper mapper;

    @Override
    public ResponseEntity<CreateUserActivityResponseDto> addUserActivity(CreateUserActivityRequestDto createUserActivityRequestDto) {
        CreateUserActivityCommand command = mapper.map(createUserActivityRequestDto);
        UUID userActivityId = createUserActivityUseCase.execute(command);
        CreateUserActivityResponseDto createUserActivityResponseDto = new CreateUserActivityResponseDto(userActivityId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createUserActivityResponseDto);
    }
}
