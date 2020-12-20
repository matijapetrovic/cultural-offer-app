package cultureapp.domain.core;

import cultureapp.domain.user.RegularUser;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public interface EmailSender {
    void sendEmail(String to, String subject, String text);
    void notifySubscribers(List<SubscriberDTO> subscribers, Long culturalOfferId);

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    class SubscriberDTO {
        Long id;
        String email;
        String firstName;
        String lastName;

        public static SubscriberDTO of(RegularUser user) {
            return new SubscriberDTO(
                    user.getId(),
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName());
        }
    }
}
