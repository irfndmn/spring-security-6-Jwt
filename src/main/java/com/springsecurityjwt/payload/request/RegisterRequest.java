package com.springsecurityjwt.payload.request;


import com.springsecurityjwt.payload.request.abstracts.BaseUserRequest;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class RegisterRequest extends BaseUserRequest {

}
