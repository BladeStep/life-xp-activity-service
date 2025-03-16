package com.bladestepapp.lifexpactivityserviceinfrastructure.adapter;

import com.bladestepapp.lifexpactivityservicecore.event.ActivityUpdatedEvent;
import com.bladestepapp.lifexpactivityservicecore.event.ActivityUpdatedMessagePort;
import com.bladestepapp.lifexpactivityserviceinfrastructure.event.ActivityUpdatedEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ActivityUpdatedMessageAdapter implements ActivityUpdatedMessagePort {

    private final ActivityUpdatedEventProducer producer;

    @Override
    public void send(UUID activityId) {
        producer.send(new ActivityUpdatedEvent(activityId,"UPDATED"));
    }
}