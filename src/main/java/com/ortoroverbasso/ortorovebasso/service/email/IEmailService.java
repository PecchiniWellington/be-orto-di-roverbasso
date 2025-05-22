package com.ortoroverbasso.ortorovebasso.service.email;

public interface IEmailService {
    void send(String to, String subject, String text);

    String buildResetPasswordEmail(String name, String resetUrl);

    void sendHtmlResetPasswordEmail(String to, String name, String resetUrl);

}
