package com.bladestepapp.lifexpactivityserviceinfrastructure.adapter;

import com.bladestepapp.lifexpactivityservicecore.persistence.DeleteActivityPort;
import com.bladestepapp.lifexpactivityserviceinfrastructure.entity.ActivityEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ActivityMongoDeleteAdapter implements DeleteActivityPort {

    private final MongoTemplate mongoTemplate;

    @Override
    public long delete(UUID id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.remove(query, ActivityEntity.class).getDeletedCount();
    }
}
