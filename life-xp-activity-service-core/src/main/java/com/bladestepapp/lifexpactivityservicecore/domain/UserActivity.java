package com.bladestepapp.lifexpactivityservicecore.domain;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserActivity {

    private UUID userId;

    private UUID activityId;

    private Integer customXp;

    public static UserActivity create(
            UUID userId,
            UUID activityId,
            Integer customXp
    ) {
        return new UserActivity(userId,
                activityId,
                customXp
        );
    }
}
