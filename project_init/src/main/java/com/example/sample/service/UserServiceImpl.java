package com.example.sample.service;

import com.example.sample.config.jwt.JwtTokenProvider;
import com.example.sample.dto.request.LoginRequest;
import com.example.sample.dto.request.SignUpRequest;
import com.example.sample.dto.response.ApiResponse;
import com.example.sample.dto.response.AuthenticationResponse;
import com.example.sample.dto.response.UserResponse;
import com.example.sample.enums.LoginType;
import com.example.sample.enums.Role;
import com.example.sample.exception.AppException;
import com.example.sample.mapper.UserMapper;
import com.example.sample.model.Credentials;
import com.example.sample.model.User;
import com.example.sample.repository.UserRepository;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider tokenProvider;
	private final PasswordEncoder passwordEncoder;
	private final UserMapper userMapper;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager,
	                       JwtTokenProvider tokenProvider, PasswordEncoder passwordEncoder, UserMapper userMapper) {

		this.userRepository = userRepository;
		this.authenticationManager = authenticationManager;
		this.tokenProvider = tokenProvider;
		this.passwordEncoder = passwordEncoder;
		this.userMapper = userMapper;
	}

	@Override
	public ApiResponse<UserResponse> registerUser(SignUpRequest signUpRequest) {

		if (!StringUtils.equals(signUpRequest.getPassword(), signUpRequest.getConfirmPassword())) {
			throw new AppException(HttpStatus.BAD_REQUEST, "password do not match");
		}

		if (BooleanUtils.isTrue(userRepository.existsByUserName(signUpRequest.getUsername()))) {
			throw new AppException(HttpStatus.BAD_REQUEST, "Username is already taken!");
		}

		if (BooleanUtils.isTrue(userRepository.existsByEmail(signUpRequest.getEmail()))) {
			throw new AppException(HttpStatus.BAD_REQUEST, "Email Address already in use!");
		}

		User user = new User();

		user.setUserId(UUID.randomUUID().toString());

		user.setUserName(signUpRequest.getUsername());
		user.setEmail(signUpRequest.getEmail());

		Set<Role> roles = Set.of(Role.END_USER);
		user.setRoles(roles);

		Credentials credentials = new Credentials();

		credentials.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		credentials.setLoginType(LoginType.DEFAULT);

		user.setCreateTime(ZonedDateTime.now());
		user.setUpdateTime(ZonedDateTime.now());

		String jwt = tokenProvider.generateToken(user.getUserId());

		credentials.setAccessToken(jwt);

		user.setCredentials(credentials);

		UserResponse userResponse = userMapper
				.mapToUserResponse(user, new AuthenticationResponse(jwt));

		User result = userRepository.save(user);

		return new ApiResponse<>(userResponse, HttpStatus.OK, true);
	}

	@Override
	public ApiResponse<UserResponse> authenticateUser(LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginRequest.getUserNameOrEmail(),
						loginRequest.getPassword()
				)
		);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		Optional<User> authenticatedUserOptional = userRepository
				.findByUserNameOrEmail(loginRequest.getUserNameOrEmail(), loginRequest.getUserNameOrEmail());

		if (authenticatedUserOptional.isEmpty()) {
			throw new AppException(HttpStatus.NOT_FOUND, "User not found");
		}

		User authenticatedUser = authenticatedUserOptional.get();

		String accessToken = tokenProvider.generateToken(authenticatedUser.getUserId());
		authenticatedUser.getCredentials().setAccessToken(accessToken);

		userRepository.save(authenticatedUser);

		UserResponse userResponse = userMapper
				.mapToUserResponse(authenticatedUser, new AuthenticationResponse(accessToken));

		return new ApiResponse<>(userResponse, HttpStatus.OK, true);
	}

	@Override
	public ApiResponse<UserResponse> getUserProfile(String id) {
		Optional<User> userOptional = userRepository.findById(id);

		if (userOptional.isEmpty()) {
			throw new AppException(HttpStatus.NOT_FOUND, "User not found");
		}

		User user = userOptional.get();

		UserResponse userResponse = userMapper.mapToUserResponse(user, new AuthenticationResponse(user.getCredentials().getAccessToken()));

		return new ApiResponse<>(userResponse, HttpStatus.OK, true);
	}
}
