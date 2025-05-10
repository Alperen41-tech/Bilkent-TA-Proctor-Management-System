package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.Services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendEmail(String userMail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userMail);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("karslioglualperen41@gmail.com"); // Optional if configured as default
        mailSender.send(message);
    }
}
