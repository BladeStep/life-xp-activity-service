package com.bladestepapp.lifexpactivityserviceinfrastructure.repository;

import com.bladestepapp.lifexpactivityserviceinfrastructure.entity.UserActivityEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserActivityRepository extends MongoRepository<UserActivityEntity, UUID> {

    List<UserActivityEntity> findByUserId(UUID userId);
}