package cultureapp.infrastructure.email;

import cultureapp.domain.core.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class EmailService implements EmailSender {
    private final JavaMailSender mailSender;
    private final Environment env;

    @Override
    @Async
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setFrom(Objects.requireNonNull(env.getProperty("spring.mail.username")));
        mail.setSubject(subject);
        mail.setText(text);
        mailSender.send(mail);
    }

    @Override
    @Async
    public void notifySubscribers(List<SubscriberDTO> subscribers, Long culturalOfferId) {
        subscribers.forEach(subscriber -> this.notifySubscriber(subscriber, culturalOfferId));
    }

    private void notifySubscriber(SubscriberDTO subscriber, Long culturalOfferId) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(subscriber.getEmail());
        mail.setFrom(Objects.requireNonNull(env.getProperty("spring.mail.username")));
        mail.setSubject("Cultural offer subscription notification");
        mail.setText(buildNotificationMessage(subscriber, culturalOfferId));
        mailSender.send(mail);
    }

    private String buildNotificationMessage(SubscriberDTO subscriber, Long culturalOfferId) {
        String message = String.format("Dear %s %s,\n\n", subscriber.getFirstName(), subscriber.getLastName());
        message += "We are informing you that a cultural offer that you are subscribed to has updates.\n";
        message += "Please visit the following link to view them: \n";
        message += String.format("http:://localhost:4200/cultural-offers/%d\n", culturalOfferId);


        return message;
    }

}