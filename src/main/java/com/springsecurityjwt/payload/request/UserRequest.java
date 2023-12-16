package com.springsecurityjwt.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springsecurityjwt.payload.request.abstracts.BaseUserRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class UserRequest extends BaseUserRequest {

    private Boolean isAdmin;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean built_in;
}