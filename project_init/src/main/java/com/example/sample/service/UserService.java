package com.example.sample.service;

import com.example.sample.dto.request.LoginRequest;
import com.example.sample.dto.request.SignUpRequest;
import com.example.sample.dto.response.ApiResponse;
import com.example.sample.dto.response.UserResponse;

public interface UserService {

    ApiResponse<UserResponse> registerUser(SignUpRequest signUpRequest);

    ApiResponse<UserResponse> authenticateUser(LoginRequest loginRequest);

    ApiResponse<UserResponse> getUserProfile(String id);

}
