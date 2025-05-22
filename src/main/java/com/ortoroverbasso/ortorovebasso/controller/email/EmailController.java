package com.ortoroverbasso.ortorovebasso.controller.email;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ortoroverbasso.ortorovebasso.dto.email.EmailRequestDto;
import com.ortoroverbasso.ortorovebasso.service.email.IEmailService;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final IEmailService emailService;

    public EmailController(IEmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequestDto request) {
        emailService.send(request.getTo(), request.getSubject(), request.getBody());
        return ResponseEntity.ok("Email inviata");
    }
}
