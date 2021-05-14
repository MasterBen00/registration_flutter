package com.example.sample.mapper;

import com.example.sample.dto.response.AuthenticationResponse;
import com.example.sample.dto.response.UserResponse;
import com.example.sample.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
	public UserResponse mapToUserResponse(User user, AuthenticationResponse authenticationResponse) {
		UserResponse userResponse = new UserResponse();

		userResponse.setUserId(user.getUserId());
		userResponse.setUserName(user.getUserName());
		userResponse.setEmail(user.getEmail());
		userResponse.setType(user.getRoles().stream().iterator().next().getAuthorityName());
		userResponse.setAuthenticationResponse(authenticationResponse);

		return userResponse;
	}
}
