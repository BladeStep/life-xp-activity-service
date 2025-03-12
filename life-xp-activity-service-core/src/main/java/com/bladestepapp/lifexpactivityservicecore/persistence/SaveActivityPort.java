package com.bladestepapp.lifexpactivityservicecore.persistence;

import com.bladestepapp.lifexpactivityservicecore.domain.Activity;

import java.util.UUID;

public interface SaveActivityPort {

    UUID save(Activity activity);
}