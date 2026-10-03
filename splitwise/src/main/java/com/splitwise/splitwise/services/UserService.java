package com.splitwise.splitwise.services;

import com.splitwise.splitwise.dtos.request.UserLoginRequest;
import com.splitwise.splitwise.dtos.request.UserSignUpRequest;
import com.splitwise.splitwise.entities.User;

public interface UserService {
    User createUser(UserSignUpRequest userSignUpRequest);
    User login(UserLoginRequest userLoginRequest);
}
