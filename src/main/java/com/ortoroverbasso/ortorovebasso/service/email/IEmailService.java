package com.ortoroverbasso.ortorovebasso.service.email;

public interface IEmailService {
    void send(String to, String subject, String text);
}
