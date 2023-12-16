
package com.springsecurityjwt.contactmessage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ContactMessageRepository extends JpaRepository <ContactMessage, Long> {


    boolean existsByContactMessageEmailEqualsAndContactMessageDateEquals(String contactMessageEmail, LocalDate now);

    Page<ContactMessage> findByContactMessageEmailEquals(String email, Pageable pageable);

    Page<ContactMessage> findByContactMessageSubjectEquals(String subject, Pageable pageable);
}

