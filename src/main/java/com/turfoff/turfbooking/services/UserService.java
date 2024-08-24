package com.turfoff.turfbooking.services;

import com.turfoff.turfbooking.domain.entities.UserEntity;

import java.util.Optional;

public interface UserService {
    UserEntity saveUser(UserEntity user);
    Optional<UserEntity> getUserById(long id);
}
