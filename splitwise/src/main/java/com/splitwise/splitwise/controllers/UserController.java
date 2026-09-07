package com.splitwise.splitwise.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> userSignUp(@RequestBody UserSignUpRequest userSignUpRequest){
        return ResponseEntity.ok("");
    }
}
