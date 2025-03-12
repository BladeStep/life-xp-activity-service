package com.bladestepapp.lifexpactivityserviceapi.controller;

import com.bladestepapp.api.ActivityCommandApi;
import com.bladestepapp.lifexpactivityserviceapi.mapper.ActivityMapper;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.CreateActivityCommand;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.CreateActivityUseCase;
import com.bladestepapp.model.ActivityResponseDto;
import com.bladestepapp.model.CreateActivityRequestDto;
import com.bladestepapp.model.CreateActivityResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ActivityCommandController implements ActivityCommandApi {

    private final CreateActivityUseCase createActivityUseCase;

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
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Delete activity is not implemented yet");
    }

    @Override
    public ResponseEntity<ActivityResponseDto> updateActivity(UUID id, CreateActivityRequestDto createActivityRequestDto) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Update activity is not implemented yet");
    }
}