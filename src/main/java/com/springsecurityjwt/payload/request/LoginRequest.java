package com.springsecurityjwt.payload.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LoginRequest {

    @Email
    @NotNull(message = "Email must not be empty")
    private String email;

    @NotNull(message = "Password must not be empty")
    private String password;

}