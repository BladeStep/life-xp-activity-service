package com.bladestepapp.lifexpactivityserviceinfrastructure.repositories;

import com.bladestepapp.lifexpactivityserviceinfrastructure.entities.ActivityEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ActivityRepository extends MongoRepository<ActivityEntity, UUID> {
}