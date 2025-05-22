package com.ortoroverbasso.ortorovebasso.service.email.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.ortoroverbasso.ortorovebasso.service.email.IEmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements IEmailService {

    private final JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void send(String to, String subject, String htmlContent) {
        sendHtmlEmail(to, subject, htmlContent);
    }

    private void sendHtmlEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            helper.setFrom("info@wellintone.com");

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Errore durante l'invio dell'email", e);
        }
    }

    @Override
    public void sendHtmlResetPasswordEmail(String to, String name, String resetUrl) {
        String htmlContent = buildResetPasswordEmail(name, resetUrl);
        sendHtmlEmail(to, "Resetta la tua password", htmlContent);
    }

    @Override
    public String buildResetPasswordEmail(String name, String resetUrl) {
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("resetUrl", resetUrl);
        return templateEngine.process("reset-password", context);
    }
}
