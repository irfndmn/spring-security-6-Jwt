package com.springsecurityjwt.service.validator;


import com.springsecurityjwt.entity.User;
import com.springsecurityjwt.exception.ConflictException;
import com.springsecurityjwt.payload.message.ErrorMessages;
import com.springsecurityjwt.payload.request.abstracts.BaseUserRequest;
import com.springsecurityjwt.repository.UserRepository;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UniquePropertyValidator {

    private final UserRepository userRepository;


    public void checkDuplicate(String email, String phoneNumber) {
        if (!email.isEmpty()) {
            if (userRepository.existsByEmail(email)) {
                throw new ConflictException(ErrorMessages.ALREADY_EXIST_BY_EMAIL);
            }
        }

        if (!phoneNumber.isEmpty()) {
            if (userRepository.existsByPhoneNumber(phoneNumber)) {
                throw new ConflictException(ErrorMessages.ALREADY_EXIST_BY_PHONE_NUMBER);
            }
        }
    }

    public void checkUniqueProperty(User user, BaseUserRequest baseUserRequest) {

        String email = "";
        String phoneNumber = "";

        if (!user.getEmail().equalsIgnoreCase(baseUserRequest.getEmail())) {
            email = baseUserRequest.getEmail();
        }
        if (!user.getPhoneNumber().equals(baseUserRequest.getPhoneNumber())) {
            phoneNumber = baseUserRequest.getPhoneNumber();
        }
        checkDuplicate(email, phoneNumber);
    }

}