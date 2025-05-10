package com.example.lab9.controller;

import com.example.lab9.dto.MassEmailRequest;
import com.example.lab9.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emails")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-to-students")
    public ResponseEntity<String> sendMassEmails(@RequestBody MassEmailRequest request) {
        emailService.sendMassEmails(
                request.getEmails(),
                request.getSubject(),
                request.getBody()
        );
        return ResponseEntity.ok("Mass emails sent successfully.");
    }
}
