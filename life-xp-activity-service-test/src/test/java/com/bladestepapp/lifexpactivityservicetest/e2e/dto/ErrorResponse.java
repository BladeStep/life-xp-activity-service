package com.bladestepapp.lifexpactivityservicetest.e2e.dto;

import lombok.Data;

@Data
public class ErrorResponse {
    private String message;
    private String error;
    private int status;
    private String path;
} 