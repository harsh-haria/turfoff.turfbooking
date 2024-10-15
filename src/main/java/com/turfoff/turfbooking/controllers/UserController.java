package com.turfoff.turfbooking.controllers;

import com.turfoff.turfbooking.domain.mysql.dto.UserDto;
import com.turfoff.turfbooking.domain.mysql.dto.UserLoggedInDto;
import com.turfoff.turfbooking.domain.mysql.dto.UserLoginDto;
import com.turfoff.turfbooking.domain.mysql.entities.UserEntity;
import com.turfoff.turfbooking.jwt.JwtUtils;
import com.turfoff.turfbooking.mappers.impl.UserMapperImpl;
import com.turfoff.turfbooking.services.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    public ResponseEntity<String> isServerAlive() {
        return new ResponseEntity<>("Server alive", HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<Map<String, Object>> addNewUser(@RequestBody UserDto userDto) {
        Map<String, Object> response = new HashMap<>();
        try {
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userDto.setRefferalCode(RandomStringUtils.randomAlphanumeric(6).toUpperCase());
            UserEntity userEntity = userMapper.mapFrom(userDto);
            userEntity.setCreatedAt(LocalDateTime.now());
            userService.saveUser(userEntity);
            response.put("message", "New user created");
            response.put("status", 201);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            response.put("message", "Phone number or email is already taken.");
            response.put("status", 400);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "An error occured while registering you");
            response.put("status", 500);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable long userId) {
        Optional<UserEntity> userById = userService.getUserById(userId);
        return userById.map(userEntity -> {
            UserDto userDto = userMapper.mapTo(userEntity);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/signin")
    public ResponseEntity userSignIn(@RequestBody UserLoginDto userLoginDto) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDto.getUsername(), userLoginDto.getPassword()));
        } catch (AuthenticationException e) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", e.getMessage());
            map.put("status", 400);
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        String authToken = jwtUtils.generateJwtTokenFromUsername(userDetails);
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
        UserLoggedInDto userLoggedInDto = new UserLoggedInDto(username, authToken, roles);
        return new ResponseEntity<>(userLoggedInDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ROLE_USER')")
    @PatchMapping("/updatePassword/{username}")
    public ResponseEntity changeUserPassword(@PathVariable String username, @RequestBody UserDto userDto) {
        String receivedPassword = userDto.getPassword();
        String newPassword = passwordEncoder.encode(receivedPassword);
        userService.updatePassword(username, newPassword);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ROLE_USER')")
    @PatchMapping("/updateEmail/{username}")
    public ResponseEntity changeUserEmail(@PathVariable String username, @RequestBody UserDto userDto) {
        String receivedEmail = userDto.getEmail();
        userService.updateEmail(username, receivedEmail);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ROLE_USER')")
    @PatchMapping("/updatePhone/{username}")
    public ResponseEntity changeUserPhone(@PathVariable String username, @RequestBody UserDto userDto) {
        String receivedPhone = userDto.getPhone();
        userService.updatePhone(username, receivedPhone);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
