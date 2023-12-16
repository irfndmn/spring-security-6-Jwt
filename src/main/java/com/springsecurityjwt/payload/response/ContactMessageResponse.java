
package com.springsecurityjwt.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
public class ContactMessageResponse implements Serializable {


    private String contactMessageName;

    private String contactMessageEmail;

    private String contactMessageSubject;

    private String contactMessageMessage;

    private LocalDate contactMessageDate;
}
