package com.springsecurityjwt.payload.request.abstracts;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@SuperBuilder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseUserRequest {

    @NotNull(message = "Please enter first name")
    @Size(min = 3,max = 20,message = "First name should be at least 3 chars")
    @Pattern(regexp="\\A(?!\\s*\\Z).+",message="Lesson name must consist of the characters a-z and 0-9.")
    private String firstName;

    @NotNull(message = "Please enter last name")
    @Size(min = 3,max = 20,message = "Last name should be at least 3 chars")
    @Pattern(regexp="\\A(?!\\s*\\Z).+",message="Lesson name must consist of the characters a-z and 0-9.")
    private String lastName;

    @Email(message = "Please enter valid email")
    @NotNull(message = "Please enter your email")
    @Size(min = 5,max = 50,message = "Your email should be between 5 and 50 chars")
    private String email;

    @NotNull(message = "Please enter password")
    //@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$") TODO: eklenecek daha sonra
    @Size(min = 8,max = 60,message = "Your password should be between 8 and 60 chars")
    private String password;

    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your phoneNumber must consist of the characters .")
    @NotNull(message = "Please enter phone number")
    @Size(min=12,max=12, message="Your phoneNumber should be 12 chars long")
    private String phoneNumber;

    @NotNull(message = "Please enter address")
    @Size(min = 8,max = 160,message = "Your address should be between 8 and 160 chars")
    private String address;

    @NotNull(message = "Please enter zipcode")
    private String zipCode;

}