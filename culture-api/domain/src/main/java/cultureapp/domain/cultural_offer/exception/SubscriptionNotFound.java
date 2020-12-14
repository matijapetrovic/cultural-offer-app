package cultureapp.domain.cultural_offer.exception;

public class SubscriptionNotFound extends Throwable {
    public SubscriptionNotFound(Long userId, Long culturalOfferId) {
        super(String.format("User with id %d isn't subscribed to offer with id %d", userId, culturalOfferId));
    }
}
