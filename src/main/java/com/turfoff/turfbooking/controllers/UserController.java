package com.turfoff.turfbooking.controllers;

import com.turfoff.turfbooking.domain.dto.UserLoggedInDto;
import com.turfoff.turfbooking.domain.dto.UserLoginDto;
import com.turfoff.turfbooking.domain.entities.UserEntity;
import com.turfoff.turfbooking.jwt.JwtUtils;
import com.turfoff.turfbooking.mappers.impl.UserMapperImpl;
import com.turfoff.turfbooking.services.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;
    private BCryptPasswordEncoder passwordEncoder;
    private UserService userService;
    private UserMapperImpl userMapper;

    public UserController(UserMapperImpl userMapper, UserService userService, JwtUtils jwtUtils) {
        this.userMapper = userMapper;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
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
        userDto.setRefferalCode(RandomStringUtils.randomAlphanumeric(6).toUpperCase());
//        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity savedUser = userService.saveUser(userDto);
        Map<String, Object> map = new HashMap<>();
        map.put("message", "New user created");
        map.put("status", 200);
        map.put("user", savedUser);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity userSignIn(@RequestBody UserLoginDto userLoginDto) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDto.getUsername(), userLoginDto.getPassword()));
        }
        catch (AuthenticationException e) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", e.getMessage());
            map.put("status", 400);
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        String authToken = jwtUtils.generateJwtTokenFromUsername(userDetails);
        List<String> roles = userDetails.getAuthorities()
                .stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());
        UserLoggedInDto userLoggedInDto = new UserLoggedInDto(username, authToken, roles);
        return new ResponseEntity<>(userLoggedInDto, HttpStatus.OK);
    }

    @GetMapping("/authCheck")
    public ResponseEntity isUserAuthenticated() {
        Map<String, Object> map = new HashMap<>();
        map.put("message", "User is authenticated");
        map.put("status", 200);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
