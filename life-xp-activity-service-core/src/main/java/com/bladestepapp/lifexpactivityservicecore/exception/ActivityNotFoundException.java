package com.bladestepapp.lifexpactivityservicecore.exception;

public class ActivityNotFoundException extends RuntimeException{

    public ActivityNotFoundException(String message) {
        super(message);
    }
}