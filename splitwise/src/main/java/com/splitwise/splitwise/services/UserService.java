package com.splitwise.splitwise.services;

import com.splitwise.splitwise.dtos.UserLoginRequest;
import com.splitwise.splitwise.dtos.UserSignUpRequest;
import com.splitwise.splitwise.entities.User;

import java.util.Optional;

public interface UserService {
    User createUser(UserSignUpRequest userSignUpRequest);
    User login(UserLoginRequest userLoginRequest);
}
