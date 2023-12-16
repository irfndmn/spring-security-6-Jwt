
package com.springsecurityjwt.contactmessage;


import com.springsecurityjwt.exception.ConflictException;
import com.springsecurityjwt.payload.message.ErrorMessages;
import com.springsecurityjwt.payload.message.SuccessMessages;
import com.springsecurityjwt.payload.request.ContactMessageRequest;
import com.springsecurityjwt.payload.response.ContactMessageResponse;
import com.springsecurityjwt.payload.response.ResponseMessage;
import com.springsecurityjwt.service.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class ContactMessageService {


    private final ContactMessageRepository contactMessageRepository;
    private final PageableHelper pageableHelper;


    public ResponseMessage<ContactMessageResponse> save(ContactMessageRequest contactMessageRequest) {
        boolean isSameMessageWithSameContactMessageEmailForToday =
                contactMessageRepository.existsByContactMessageEmailEqualsAndContactMessageDateEquals(contactMessageRequest.getContactMessageEmail(), LocalDate.now());
        if (isSameMessageWithSameContactMessageEmailForToday) {
            throw new ConflictException(ErrorMessages.ALREADY_SEND_A_MESSAGE_TODAY);
        }

        ContactMessage contactMessage = mapContactMessageRequestToContactMessage(contactMessageRequest);
        ContactMessage savedContactMessage = contactMessageRepository.save(contactMessage);

        return ResponseMessage.<ContactMessageResponse>builder()
                .object(mapContactMessageToContactMessageResponse(savedContactMessage))
                .message(SuccessMessages.CREATED_MESSAGE)
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    private ContactMessage mapContactMessageRequestToContactMessage(ContactMessageRequest contactMessageRequest) {
        return ContactMessage.builder()
                .contactMessageName(contactMessageRequest.getContactMessageName())
                .contactMessageEmail(contactMessageRequest.getContactMessageEmail())
                .contactMessageSubject(contactMessageRequest.getContactMessageSubject())
                .contactMessageMessage(contactMessageRequest.getContactMessageMessage())
                .contactMessageDate(LocalDate.now())
                .build();
    }

    private ContactMessageResponse mapContactMessageToContactMessageResponse(ContactMessage contactMessage) {
        return ContactMessageResponse.builder()
                .contactMessageName(contactMessage.getContactMessageName())
                .contactMessageSubject(contactMessage.getContactMessageSubject())
                .contactMessageMessage(contactMessage.getContactMessageMessage())
                .contactMessageEmail(contactMessage.getContactMessageEmail())
                .contactMessageDate(LocalDate.now())
                .build();
    }


    public Page<ContactMessageResponse> getAllContactMessages(int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
        return contactMessageRepository.findAll(pageable).map(this::mapContactMessageToContactMessageResponse);
    }


    public Page<ContactMessageResponse> searchByContactMessageEmail(String email, int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
        return contactMessageRepository.findByContactMessageEmailEquals(email, pageable).map(this::mapContactMessageToContactMessageResponse);
    }


    public Page<ContactMessageResponse> searchByContactMessageSubject(String subject, int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
        return contactMessageRepository.findByContactMessageSubjectEquals(subject, pageable).map(this::mapContactMessageToContactMessageResponse);
    }
}
