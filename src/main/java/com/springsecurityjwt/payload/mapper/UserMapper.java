package com.springsecurityjwt.payload.mapper;



import com.springsecurityjwt.entity.User;
import com.springsecurityjwt.entity.enums.RoleType;
import com.springsecurityjwt.payload.request.UserRequest;
import com.springsecurityjwt.payload.request.abstracts.BaseUserRequest;
import com.springsecurityjwt.payload.response.UserResponse;
import com.springsecurityjwt.payload.response.abstracts.BaseUserResponse;
import com.springsecurityjwt.service.UserRoleService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Data
@Component
@RequiredArgsConstructor
public class UserMapper {
    private final UserRoleService userRoleService;
    public User mapBaseUserRequestToUser(BaseUserRequest baseUserRequest) {
        return User.builder()
                .email(baseUserRequest.getEmail())
                .password(baseUserRequest.getPassword())
                .address(baseUserRequest.getAddress())
                .firstName(baseUserRequest.getFirstName())
                .lastName(baseUserRequest.getLastName())
                .phoneNumber(baseUserRequest.getPhoneNumber())
                .zipCode(baseUserRequest.getZipCode())
                .build();
    }

    public User mapUserRequestToUser(UserRequest userRequest) {
        return mapBaseUserRequestToUser(userRequest).toBuilder()
                .built_in(false)
                .isAdmin(true)
                .build();
    }

    public BaseUserResponse mapUserToBaseUserResponse(User user){
        String userRole="";
        if(!CollectionUtils.isEmpty(user.getUserRoles())) {
            userRole=user.getUserRoles().contains(userRoleService.getUserRoleByRoleType(RoleType.ADMIN))?RoleType.ADMIN.name():
                    user.getUserRoles().stream().findFirst().get().getRoleType().name();
        }
        return BaseUserResponse.builder()
                .Id(user.getUserId())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .zipCode(user.getZipCode())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userRole(userRole)
                .build();
    }

    public UserResponse mapUserToUserResponse(User user) {
        String userRole="";
        if(!CollectionUtils.isEmpty(user.getUserRoles())) {
            userRole=user.getUserRoles().contains(userRoleService.getUserRoleByRoleType(RoleType.ADMIN))?RoleType.ADMIN.name():
             user.getUserRoles().stream().findFirst().get().getRoleType().name();
        }
        return  UserResponse.builder()
                .Id(user.getUserId())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .zipCode(user.getZipCode())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userRole(userRole)
                .build();
    }
    public User mapUpdateUserRequestToUser(BaseUserRequest baseUserRequest, Long id) {
        return mapBaseUserRequestToUser(baseUserRequest).toBuilder()
                .userId(id)
                .build();
    }
}
