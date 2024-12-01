package com.tisitha.cshop.service;

import com.tisitha.cshop.dto.Mailbody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${mail.email}")
    private String email;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendSimpleMessage(Mailbody mailbody){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailbody.to());
        message.setFrom(email);
        message.setSubject(mailbody.subject());
        message.setText(mailbody.text());

        javaMailSender.send(message);
    }
}