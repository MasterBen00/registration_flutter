package com.example.sample.constants;

public class Constants {
    public static class UserDocument {
        public static final String USERS = "users";
    }

    public static final String[] SWAGGER_URLS = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**"
    };

    public static class ApiEndpoint {
        public static final String PUBLIC_API_PREFIX = "/p/**";
        public static final String AUTH_API_PREFIX = "/a/**";
        public static final String ROLE_AUTHORITY_PREFIX = "ROLE_";
    }
}
