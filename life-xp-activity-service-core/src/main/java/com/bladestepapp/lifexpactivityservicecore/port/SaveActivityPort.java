package com.bladestepapp.lifexpactivityservicecore.port;

import com.bladestepapp.lifexpactivityservicecore.domain.Activity;

import java.util.UUID;

public interface SaveActivityPort {

    UUID save(Activity activity);
}