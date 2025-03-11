package com.bladestepapp.lifexpactivityservicecore.repository;

import com.bladestepapp.lifexpactivityservicecore.domain.Activity;

import java.util.UUID;

public interface SaveActivityPort {

    UUID save(Activity activity);
}