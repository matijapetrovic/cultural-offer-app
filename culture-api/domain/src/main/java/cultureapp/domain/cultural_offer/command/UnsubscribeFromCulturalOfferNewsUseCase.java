package cultureapp.domain.cultural_offer.command;

import cultureapp.domain.core.validation.SelfValidating;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.cultural_offer.exception.SubscriptionNotFound;
import cultureapp.domain.regular_user.exception.RegularUserNotFound;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.Positive;

public interface UnsubscribeFromCulturalOfferNewsUseCase {
    void unsubscribe(UnsubscribeFromCulturalOfferNewsCommand command) throws RegularUserNotFound, CulturalOfferNotFoundException, SubscriptionNotFound;

    @Value
    @EqualsAndHashCode(callSuper = false)
    class UnsubscribeFromCulturalOfferNewsCommand extends SelfValidating<UnsubscribeFromCulturalOfferNewsCommand> {
        @Positive
        Long culturalOfferId;

        public UnsubscribeFromCulturalOfferNewsCommand(Long culturalOfferId) {
            this.culturalOfferId = culturalOfferId;
            this.validateSelf();
        }
    }
}
