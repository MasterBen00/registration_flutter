package com.example.sample.controller;

import com.example.sample.config.CurrentUser;
import com.example.sample.dto.response.ApiResponse;
import com.example.sample.dto.response.UserResponse;
import com.example.sample.exception.AppException;
import com.example.sample.model.User;
import com.example.sample.model.UserPrincipal;
import com.example.sample.repository.UserRepository;
import com.example.sample.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {

	private final UserRepository userRepository;
	private final UserService userService;

	@Autowired
	public UserController(UserRepository userRepository, UserService userService) {
		this.userRepository = userRepository;
		this.userService = userService;
	}

	@GetMapping("/a/user/me")
	@PreAuthorize("hasRole('ROLE_END_USER')")
	public ApiResponse<UserResponse> getCurrentUser(@CurrentUser UserPrincipal currentUser) {

		return userService.getUserProfile(currentUser.getUserId());
	}

	@ApiOperation("get user by id")
	@GetMapping("/p/user/{userId}")
	public User getUserById(@PathVariable String userId) {

		return userRepository.findById(userId)
				.orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "custom exception"));

	}
}
