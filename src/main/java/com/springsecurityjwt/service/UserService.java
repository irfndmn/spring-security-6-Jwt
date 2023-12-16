package com.springsecurityjwt.service;


import com.springsecurityjwt.entity.User;
import com.springsecurityjwt.entity.UserRole;
import com.springsecurityjwt.entity.enums.RoleType;
import com.springsecurityjwt.exception.BadRequestException;
import com.springsecurityjwt.exception.ConflictException;
import com.springsecurityjwt.payload.mapper.UserMapper;
import com.springsecurityjwt.payload.message.ErrorMessages;
import com.springsecurityjwt.payload.message.SuccessMessages;
import com.springsecurityjwt.payload.request.UserRequest;
import com.springsecurityjwt.payload.request.abstracts.BaseUserRequest;
import com.springsecurityjwt.payload.response.ResponseMessage;
import com.springsecurityjwt.payload.response.UserResponse;
import com.springsecurityjwt.repository.UserRepository;
import com.springsecurityjwt.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final UserMapper userMapper;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    public void saveUser(BaseUserRequest baseUserRequest) {
        uniquePropertyValidator.checkDuplicate(baseUserRequest.getEmail(), baseUserRequest.getPhoneNumber());
        User user;
        Set<UserRole> roleSet = new HashSet<>();
        if (baseUserRequest instanceof UserRequest) {
            UserRequest userRequest = (UserRequest) baseUserRequest;
            user = userMapper.mapUserRequestToUser(userRequest);
            if (Objects.equals(user.getEmail(), "admin@gmail.com")) {
                user.setBuilt_in(true);
            }
            UserRole roleAdmin = userRoleService.getUserRoleByRoleTypeAdmin(RoleType.ADMIN);
            roleSet.add(roleAdmin);
            user.setAdmin(true);
        } else {
            user = userMapper.mapBaseUserRequestToUser(baseUserRequest);
            UserRole role = userRoleService.getUserRoleByRoleType(RoleType.CUSTOMER);
            roleSet.add(role);
            user.setAdmin(false);
        }
        user.setUserRoles(roleSet);
        //password encode
       user.setPassword(passwordEncoder.encode(baseUserRequest.getPassword()));
        userRepository.save(user);
    }

    public boolean isAdminRegistered(String email) {
        return userRepository.countAdmin(email);
    }

    public long isAdminRegistered() {
        return userRepository.count();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmailEquals(email).orElseThrow();
    }

    public ResponseMessage<UserResponse> getAnUserInfo(Long id) {
        UserResponse userResponse = userMapper.mapUserToUserResponse(isExistUserById(id));
        return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.USER_FOUND)
                .object(userResponse)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public User isExistUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ConflictException(String.format(ErrorMessages.USER_NOT_FOUND, id)));
    }

    public ResponseMessage<UserResponse> updateAnUserInfo(Long id, UserRequest userRequest) {
        User user = isExistUserById(id);
        uniquePropertyValidator.checkUniqueProperty(user, userRequest);
        User updatedUser = userMapper.mapUpdateUserRequestToUser(userRequest, id);
        updatedUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        Set<UserRole> roleSet = new HashSet<>();
        roleSet.add(userRoleService.getUserRoleByRoleType(RoleType.CUSTOMER));
            if(userRequest.getIsAdmin()){
                roleSet.add(userRoleService.getUserRoleByRoleType(RoleType.ADMIN));
                updatedUser.setAdmin(true);
            }
        updatedUser.setUserRoles(roleSet);
        User savedUser = userRepository.save(updatedUser);
        return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.USER_UPDATE)
                .object(userMapper.mapUserToUserResponse(savedUser))
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public ResponseMessage<?> deleteUser(Long id) {
        User user = isExistUserById(id);
        if(user.isBuilt_in()){
            throw new BadRequestException(String.format(ErrorMessages.NOT_ALLOWED_DELETE_DEFAULT_USER,id));
        }
        userRepository.delete(user);
        return ResponseMessage.builder()
                .message(SuccessMessages.USER_DELETE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public List<UserResponse> getAllUserInfo() {
        return userRepository.findAll()
                .stream().map(userMapper::mapUserToUserResponse)
                .collect(Collectors.toList());
    }

    public ResponseMessage<?> createAdmin(UserRequest userRequest) {
        uniquePropertyValidator.checkDuplicate(userRequest.getEmail(), userRequest.getPhoneNumber());
        User user = userMapper.mapUserRequestToUser(userRequest);
        Set<UserRole> roleSet = new HashSet<>();
        UserRole roleAdmin = userRoleService.getUserRoleByRoleTypeAdmin(RoleType.ADMIN);
        roleSet.add(roleAdmin);
        user.setUserRoles(roleSet);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userRepository.save(user);
        return ResponseMessage.builder()
                .message(SuccessMessages.USER_REGISTERED_MESSAGE)
                .httpStatus(HttpStatus.CREATED)
                .build();
    }
}