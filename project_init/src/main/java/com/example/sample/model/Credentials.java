package com.example.sample.model;

import com.example.sample.enums.LoginType;
import lombok.Data;

import java.io.Serializable;

@Data
public class Credentials implements Serializable {
    private LoginType loginType;
    private String password;
    private String fbAccessToken;
    private String twitterAccessToken;
    private String twitterSecretToken;
    private String accessToken;
    private String refreshToken;
}
