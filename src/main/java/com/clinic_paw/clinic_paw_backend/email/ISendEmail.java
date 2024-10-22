package com.clinic_paw.clinic_paw_backend.email;

public interface ISendEmail {
    void sendEmail(String toUser, String subject, String messageBody);
}
