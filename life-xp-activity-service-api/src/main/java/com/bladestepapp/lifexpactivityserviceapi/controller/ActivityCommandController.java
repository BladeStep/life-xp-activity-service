package com.bladestepapp.lifexpactivityserviceapi.controller;

import com.bladestepapp.api.ActivityCommandApi;
import com.bladestepapp.lifexpactivityserviceapi.mapper.ActivityMapper;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.*;
import com.bladestepapp.model.ActivityResponseDto;
import com.bladestepapp.model.CreateActivityRequestDto;
import com.bladestepapp.model.CreateActivityResponseDto;
import com.bladestepapp.model.UpdateActivityRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ActivityCommandController implements ActivityCommandApi {

    private final CreateActivityUseCase createActivityUseCase;

    private final DeleteActivityUseCase deleteActivityUseCase;

    private final UpdateActivityUseCase updateActivityUseCase;

    private final ActivityMapper mapper;

    @Override
    public ResponseEntity<CreateActivityResponseDto> createActivity(CreateActivityRequestDto createActivityRequestDto) {
        CreateActivityCommand command = mapper.map(createActivityRequestDto);
        UUID activityId = createActivityUseCase.execute(command);
        CreateActivityResponseDto createActivityResponseDto = new CreateActivityResponseDto(activityId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createActivityResponseDto);
    }

    @Override
    public ResponseEntity<Void> deleteActivity(UUID id) {
        DeleteActivityCommand command = new DeleteActivityCommand(id);
        deleteActivityUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<ActivityResponseDto> updateActivity(UUID id, UpdateActivityRequestDto updateActivityRequestDto) {
        UpdateActivityCommand command = mapper.map(id, updateActivityRequestDto);
        updateActivityUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}