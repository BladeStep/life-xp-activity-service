package com.bladestepapp.lifexpactivityserviceapi.controller;

import com.bladestepapp.api.ActivityCommandApi;
import com.bladestepapp.lifexpactivityserviceapi.mapper.ActivityMapper;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.CreateActivityCommand;
import com.bladestepapp.lifexpactivityservicecore.usecase.write.CreateActivityUseCase;
import com.bladestepapp.model.ActivityResponse;
import com.bladestepapp.model.CreateActivityRequest;
import com.bladestepapp.model.CreateActivityResponse;
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
    public ResponseEntity<CreateActivityResponse> createActivity(CreateActivityRequest createActivityRequest) {
        CreateActivityCommand command = mapper.map(createActivityRequest);
        UUID activityId = createActivityUseCase.execute(command);
        CreateActivityResponse createActivityResponse = new CreateActivityResponse(activityId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createActivityResponse);
    }

    @Override
    public ResponseEntity<Void> deleteActivity(UUID id) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Delete activity is not implemented yet");
    }

    @Override
    public ResponseEntity<ActivityResponse> updateActivity(UUID id, CreateActivityRequest createActivityRequest) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Update activity is not implemented yet");
    }
}