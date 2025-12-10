package com.autonoma.service;

public interface EmailService {
    void sendOtpEmail(String to, String otp);
    void sendGenericEmail(String to, String subject, String body);
}
