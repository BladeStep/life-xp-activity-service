package com.bladestepapp.lifexpactivityservicecore.exceptions;

public class ActivityNotFoundException extends RuntimeException{

    public ActivityNotFoundException(String message) {
        super(message);
    }
}