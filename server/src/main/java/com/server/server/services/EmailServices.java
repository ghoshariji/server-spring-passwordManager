package com.server.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

// service is doing for the mailservice sending mail service
@Service
public class EmailServices {

    // doing autowired for the class getting conncetioin from a javamailsender class
    // using as a object

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String toEmail, String Subject, String body) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setFrom("arijitghosh1203@gmail.com");
        sm.setTo(toEmail);
        sm.setSubject(Subject);
        sm.setText(body);
        javaMailSender.send(sm);
    }
}
