package com.turfoff.turfbooking.controllers;

import com.turfoff.turfbooking.domain.entities.UserEntity;
import com.turfoff.turfbooking.mappers.impl.UserMapperImpl;
import com.turfoff.turfbooking.services.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private BCryptPasswordEncoder passwordEncoder;
    private UserService userService;
    private UserMapperImpl userMapper;

    public UserController(UserMapperImpl userMapper, UserService userService) {
        this.userMapper = userMapper;
        this.userService = userService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @GetMapping("/serveralive")
    public ResponseEntity isUserAlive() {
        Map<String, Object> map = new HashMap<>();
        map.put("message", "Server is alive");
        map.put("status", 200);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity addNewUser(@RequestBody UserEntity userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setRefferalCode(RandomStringUtils.randomAlphanumeric(6).toUpperCase());;
//        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity savedUser = userService.saveUser(userDto);
        Map<String, Object> map = new HashMap<>();
        map.put("message", "New user created");
        map.put("status", 200);
        map.put("user", savedUser);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

}
