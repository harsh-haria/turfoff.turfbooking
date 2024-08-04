package com.turfoff.turfbooking.services;

import com.turfoff.turfbooking.domain.entities.UserEntity;
import com.turfoff.turfbooking.repositories.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService{


    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity saveUser(UserEntity user) {
        return userRepository.save(user);
    }

//    @Override
//    public Optional<UserEntity> findByUsername(String username) {
//        return Optional.empty();
//    }

}
