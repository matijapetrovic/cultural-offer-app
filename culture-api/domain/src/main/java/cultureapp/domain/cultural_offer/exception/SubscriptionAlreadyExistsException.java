package cultureapp.domain.cultural_offer.exception;


public class SubscriptionAlreadyExistsException extends Exception {
    public SubscriptionAlreadyExistsException(Long userId, Long culturalOfferId) {
        super(String.format("User with id %d is already subscribed to offer with id %d", userId, culturalOfferId));
    }
}
