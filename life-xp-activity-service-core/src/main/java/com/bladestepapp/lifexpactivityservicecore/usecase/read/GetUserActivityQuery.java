package com.bladestepapp.lifexpactivityservicecore.usecase.read;

import lombok.Value;

import java.util.UUID;

@Value
public class GetUserActivityQuery {

    UUID userId;
}