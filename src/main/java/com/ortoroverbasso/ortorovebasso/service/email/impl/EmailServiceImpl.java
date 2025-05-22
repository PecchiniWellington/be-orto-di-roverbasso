package com.ortoroverbasso.ortorovebasso.service.email.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ortoroverbasso.ortorovebasso.service.email.IEmailService;

@Service
public class EmailServiceImpl implements IEmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void send(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("laTuaEmail@gmail.com");
        mailSender.send(message);
    }
}
