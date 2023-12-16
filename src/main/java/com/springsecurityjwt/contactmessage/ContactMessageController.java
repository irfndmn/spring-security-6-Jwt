
package com.springsecurityjwt.contactmessage;


import com.springsecurityjwt.payload.request.ContactMessageRequest;
import com.springsecurityjwt.payload.response.ContactMessageResponse;
import com.springsecurityjwt.payload.response.ResponseMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/contactMessages")
@RequiredArgsConstructor
@RestController
public class ContactMessageController {

    private final ContactMessageService contactMessageService;

    @PostMapping("/save")
    public ResponseMessage<ContactMessageResponse> saveContactMessage(@Valid
                                                                      @RequestBody ContactMessageRequest contactMessageRequest) {
        return contactMessageService.save(contactMessageRequest);
    }


    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Page<ContactMessageResponse> getAllContactMessages(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "contactMessageDate") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type) {
        return contactMessageService.getAllContactMessages(page, size, sort, type);
    }


    @GetMapping("/searchByEmail")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Page<ContactMessageResponse> searchByContactMessageEmail(
            @RequestParam(value = "contactMessageEmail") String email,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "contactMessageDate") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ) {
        return contactMessageService.searchByContactMessageEmail(email, page, size, sort, type);
    }


    @GetMapping("/searchBySubject")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Page<ContactMessageResponse> searchByContactMessageSubject(
            @RequestParam(value = "contactMessageSubject") String subject,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "contactMessageDate") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ) {
        return contactMessageService.searchByContactMessageSubject(subject, page, size, sort, type);
    }
}