package com.bladestepapp.lifexpactivityserviceinfrastructure.persistence;

import com.bladestepapp.lifexpactivityserviceinfrastructure.entity.ActivityEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ActivityRepository extends MongoRepository<ActivityEntity, UUID> {
}