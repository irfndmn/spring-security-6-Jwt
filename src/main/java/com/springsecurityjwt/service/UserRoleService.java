package com.springsecurityjwt.service;



import com.springsecurityjwt.entity.UserRole;
import com.springsecurityjwt.entity.enums.RoleType;
import com.springsecurityjwt.exception.ConflictException;
import com.springsecurityjwt.payload.message.ErrorMessages;
import com.springsecurityjwt.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRole getUserRoleByRoleType(RoleType roleType) {
        return userRoleRepository.findByEnumRoleEquals(roleType).orElseThrow(() ->
                new ConflictException(ErrorMessages.USER_ROLE_NOT_FOUND_BY_ROLE_TYPE));
    }

    public UserRole getUserRoleByRoleTypeAdmin(RoleType roleType) {
        return userRoleRepository.findByEnumRoleEquals(roleType).orElseThrow(() ->
                new ConflictException(ErrorMessages.USER_ROLE_NOT_FOUND_BY_ROLE_TYPE));
    }

    public List<UserRole> getAllUserRole() {
        return userRoleRepository.findAll();
    }

    public UserRole save(RoleType roleType) {
        if (userRoleRepository.existsByEnumRoleEquals(roleType)) {
            throw new ConflictException(ErrorMessages.USER_ROLE_ALREADY_EXIST);
        }
        UserRole userRole = UserRole
                .builder()
                .roleType(roleType)
                .build();
        return userRoleRepository.save(userRole);
    }
}