package com.splitwise.splitwise.services.impl;

import com.splitwise.splitwise.dtos.UserLoginRequest;
import com.splitwise.splitwise.dtos.UserSignUpRequest;
import com.splitwise.splitwise.entities.User;
import com.splitwise.splitwise.exceptions.InvalidCredentialsException;
import com.splitwise.splitwise.exceptions.ResourceAlreadyExist;
import com.splitwise.splitwise.repositories.UserRepo;
import com.splitwise.splitwise.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;


    @Override
    @Transactional
    public User createUser(UserSignUpRequest userSignUpRequest) {
        if(userRepo.existsByEmail(userSignUpRequest.email())){
            throw new ResourceAlreadyExist("user with provided email "
                    + userSignUpRequest.email() + " already exist");
        }
        User user = User.builder()
                .name(userSignUpRequest.name())
                .email(userSignUpRequest.email())
                .password(userSignUpRequest.password())
                .build();

        return userRepo.save(user);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {
        User user = userRepo.findByEmail(userLoginRequest.email())
                .orElseThrow(()-> new InvalidCredentialsException("invalid credentials"));
        if (!user.getPassword().equals(userLoginRequest.password())){
            throw new InvalidCredentialsException("invalid email or password");
        }
        return user;
    }
}
