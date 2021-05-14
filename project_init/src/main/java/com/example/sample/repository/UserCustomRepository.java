package com.example.sample.repository;

import com.example.sample.model.User;

import java.util.Optional;

public interface UserCustomRepository {

    Optional<User> findByUserNameOrEmail(String param1, String param2);
}
