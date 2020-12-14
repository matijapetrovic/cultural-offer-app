package cultureapp.domain.cultural_offer.exception;


public class SubscriptionAlreadyExists extends Throwable {
    public SubscriptionAlreadyExists(Long userId, Long culturalOfferId) {
        super(String.format("User with id %d is already subscribed to offer with id %d", userId, culturalOfferId));
    }
}
