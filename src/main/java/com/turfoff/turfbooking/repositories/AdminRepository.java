package com.turfoff.turfbooking.repositories;

import com.turfoff.turfbooking.domain.entities.AdminEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AdminRepository extends CrudRepository<AdminEntity, UUID> {
}
