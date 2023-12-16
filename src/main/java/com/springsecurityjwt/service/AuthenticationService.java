package com.springsecurityjwt.service;


import com.springsecurityjwt.payload.request.LoginRequest;
import com.springsecurityjwt.payload.request.abstracts.BaseUserRequest;
import com.springsecurityjwt.security.jwt.JWTUtils;
import com.springsecurityjwt.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;

    public void save(BaseUserRequest baseUserRequest) {
        userService.saveUser(baseUserRequest);
    }

    public ResponseEntity<Map<String, String>> loginUser(LoginRequest loginRequest) {

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        Authentication authenticated =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authenticated);

        UserDetailsImpl userDetails = (UserDetailsImpl) authenticated.getPrincipal();
        String token = jwtUtils.generateToken(userDetails);
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        String roleString = "";
        for (String role : roles) {
            roleString = role + " ";
        }
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("roles", roleString);
        response.put("email", email);
        response.put("name", userDetails.getName());
        return ResponseEntity.ok(response);
    }
}