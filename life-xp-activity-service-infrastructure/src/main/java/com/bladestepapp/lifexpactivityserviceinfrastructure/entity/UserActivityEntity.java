package com.bladestepapp.lifexpactivityserviceinfrastructure.entity;

import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "user_activities")
@EqualsAndHashCode
public class UserActivityEntity {

    @Id
    private UUID id;

    private UUID userId;// ID пользователя

    private UUID activityId; // Ссылка на активность

    private Integer customXp; // Пользовательское значение XP (может быть null)

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getActivityId() {
        return activityId;
    }

    public void setActivityId(UUID activityId) {
        this.activityId = activityId;
    }

    public Integer getCustomXp() {
        return customXp;
    }

    public void setCustomXp(Integer customXp) {
        this.customXp = customXp;
    }
}
