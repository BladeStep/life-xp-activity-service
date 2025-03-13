package com.bladestepapp.lifexpactivityserviceinfrastructure.gateway.model;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserModelResponse {

    private UUID id;

    private String name;

    private String email;
}
