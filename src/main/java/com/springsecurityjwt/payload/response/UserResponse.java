package com.springsecurityjwt.payload.response;


import com.springsecurityjwt.payload.response.abstracts.BaseUserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class UserResponse extends BaseUserResponse {


}
