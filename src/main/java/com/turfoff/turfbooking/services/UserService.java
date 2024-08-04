package com.turfoff.turfbooking.services;

import com.turfoff.turfbooking.domain.entities.UserEntity;

public interface UserService {
    UserEntity saveUser(UserEntity user);
}
