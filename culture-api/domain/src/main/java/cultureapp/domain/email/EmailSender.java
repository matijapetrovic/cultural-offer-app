package cultureapp.domain.email;

public interface EmailSender {
    void sendEmail(String to, String subject, String text);
}
