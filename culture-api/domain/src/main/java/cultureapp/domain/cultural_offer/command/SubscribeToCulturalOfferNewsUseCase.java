package cultureapp.domain.cultural_offer.command;

import cultureapp.domain.core.validation.SelfValidating;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.cultural_offer.exception.SubscriptionAlreadyExists;
import cultureapp.domain.regular_user.exception.RegularUserNotFound;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.Positive;

public interface SubscribeToCulturalOfferNewsUseCase {
    void subscribe(SubscribeToCulturalOfferNewsCommand command)
            throws RegularUserNotFound, CulturalOfferNotFoundException, SubscriptionAlreadyExists;

    @Value
    @EqualsAndHashCode(callSuper = false)
    class SubscribeToCulturalOfferNewsCommand extends SelfValidating<SubscribeToCulturalOfferNewsCommand> {
        @Positive
        Long culturalOfferId;

        public SubscribeToCulturalOfferNewsCommand(Long culturalOfferId) {
            this.culturalOfferId = culturalOfferId;
            this.validateSelf();
        }
    }
}
