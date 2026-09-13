package com.splitwise.splitwise.controllers;

import com.splitwise.splitwise.dtos.*;
import com.splitwise.splitwise.entities.User;
import com.splitwise.splitwise.payload.ApiResponse;
import com.splitwise.splitwise.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<CommonUserResponse>> userSignUp(@RequestBody @Valid UserSignUpRequest userSignUpRequest){
        User newuser = userService.createUser(userSignUpRequest);
        CommonUserResponse responseDto = new CommonUserResponse(
                newuser.getId(), newuser.getName(), newuser.getEmail()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("user signed successfuly", responseDto));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<CommonUserResponse>> userLogin(@RequestBody @Valid UserLoginRequest userLoginRequest){
        User newuser = userService.login(userLoginRequest);
        CommonUserResponse responseDto = new CommonUserResponse(
                newuser.getId(),
                newuser.getName(),
                newuser.getEmail()
        );
        return ResponseEntity.ok(
                ApiResponse.success("Login successfull", responseDto)
        );
    }
}
