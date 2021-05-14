package com.example.sample.controller;

import com.example.sample.dto.request.LoginRequest;
import com.example.sample.dto.request.SignUpRequest;
import com.example.sample.dto.response.ApiResponse;
import com.example.sample.dto.response.UserResponse;
import com.example.sample.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation("register a new user")
    @PostMapping("/p/signup")
    public ApiResponse<UserResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        return userService.registerUser(signUpRequest);
    }

    @ApiOperation("user sign in api")
    @PostMapping("/p/signin")
    public ApiResponse<UserResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        return userService.authenticateUser(loginRequest);
    }
}
