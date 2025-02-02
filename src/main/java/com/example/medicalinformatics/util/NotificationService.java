package com.example.medicalinformatics.util;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void sendEmail(String to, String subject, String body) {
        // TODO: Implement email sending logic
        System.out.println("Sending email to: " + to + " with subject: " + subject);
    }

    public void sendSMS(String phoneNumber, String message) {
        // TODO: Implement SMS sending logic
        System.out.println("Sending SMS to: " + phoneNumber + " with message: " + message);
    }
}

