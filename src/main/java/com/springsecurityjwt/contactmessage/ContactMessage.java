
package com.springsecurityjwt.contactmessage;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ContactMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactMessageId;

    @NotNull
    private String contactMessageName;

    @NotNull
    private String contactMessageEmail;

    @NotNull
    private String contactMessageSubject;

    @NotNull
    private String contactMessageMessage;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd" )
    private LocalDate contactMessageDate;
}

