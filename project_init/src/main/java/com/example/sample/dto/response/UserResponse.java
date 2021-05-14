package com.example.sample.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserResponse implements Serializable {
	private String userId;
	private String userName;
	private String email;
	private String type;
	private AuthenticationResponse authenticationResponse;
}
