
package com.springsecurityjwt.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ContactMessageRequest implements Serializable {


    @NotNull(message = "Please enter name.")
    @Size(min = 3,max = 20, message = "Your name must be a minimum of 3 characters and a maximum of 20 characters.")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+",message = "Your name must consist of the character.")
    private String contactMessageName;

    @NotNull(message = "Please enter email.")
    @Email(message = "Please enter valid email.")
    private String contactMessageEmail;

    @NotNull(message = "Please enter subject.")
    @Size(min = 4, max = 60, message = "Your subject should be at least 4 characters.")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+",message = "Your subject must consist of the character.")
    private String contactMessageSubject;

    @NotNull(message = "Please enter message.")
    @Size(min = 4,max = 100,message = "Your message should be at least 4 characters.")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+",message = "Your message must consist of the character.")
    private String contactMessageMessage;

}