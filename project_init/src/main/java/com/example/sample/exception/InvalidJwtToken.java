package com.example.sample.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidJwtToken extends AuthenticationException {
    public InvalidJwtToken(String msg) {
        super(msg);
    }

    public InvalidJwtToken(String msg, Throwable cause) {
        super(msg, cause);
    }
}
