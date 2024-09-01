package com.turfoff.turfbooking.repositories.mongo;

import com.turfoff.turfbooking.domain.mongo.entities.TurfEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurfRepository extends MongoRepository<TurfEntity,String> {
}
