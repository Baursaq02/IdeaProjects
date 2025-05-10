package com.example.lab9.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("fromEmail");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    // Новый метод для массовой отправки
    public void sendMassEmails(List<String> emails, String subject, String body) {
        for (String email : emails) {
            try {
                sendSimpleMessage(email, subject, body);
            } catch (Exception e) {
                System.err.println("❌ Ошибка при отправке на: " + email);
                e.printStackTrace();
            }
        }
    }
}
