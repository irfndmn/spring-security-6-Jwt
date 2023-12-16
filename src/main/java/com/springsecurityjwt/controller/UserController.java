package com.springsecurityjwt.controller;



import com.springsecurityjwt.payload.request.UserRequest;
import com.springsecurityjwt.payload.response.ResponseMessage;
import com.springsecurityjwt.payload.response.UserResponse;
import com.springsecurityjwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public List<UserResponse> getAllUserInfo() {
        return userService.getAllUserInfo();
    }

    @GetMapping("/{id}")
    public ResponseMessage<UserResponse> getAnUserInfo(@PathVariable Long id) {
        return userService.getAnUserInfo(id);
    }

    @PutMapping("/{id}")
    public ResponseMessage<UserResponse> updateAnUserInfo(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        return userService.updateAnUserInfo(id, userRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<?> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @PostMapping("/createAdmin")
    public ResponseMessage<?> createAdmin(@RequestBody @Valid UserRequest userRequest) {
        return userService.createAdmin(userRequest);
    }
}