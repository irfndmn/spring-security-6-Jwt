package com.springsecurityjwt.controller;


import com.springsecurityjwt.payload.message.SuccessMessages;
import com.springsecurityjwt.payload.request.LoginRequest;
import com.springsecurityjwt.payload.request.RegisterRequest;
import com.springsecurityjwt.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<String> saveUser(@RequestBody @Valid RegisterRequest registerRequest) {

        authenticationService.save(registerRequest);
        return new ResponseEntity<>(SuccessMessages.USER_REGISTERED_MESSAGE, HttpStatus.OK);
    }

    //************ login
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody @Valid LoginRequest loginRequest) {
        return authenticationService.loginUser(loginRequest);
    }
}
