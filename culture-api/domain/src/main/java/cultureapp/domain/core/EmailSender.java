package cultureapp.domain.core;

public interface EmailSender {
    void sendEmail(String to, String subject, String text);
}
