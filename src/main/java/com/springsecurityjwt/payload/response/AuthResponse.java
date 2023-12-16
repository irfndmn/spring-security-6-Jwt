package com.springsecurityjwt.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)  // Json dosyada setlenmeyen fieldlar gitmesin
public class AuthResponse {


    private Set<String> role;
    private String token;
    private String name;
    private String email;
}