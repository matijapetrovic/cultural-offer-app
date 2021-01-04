package cultureapp.domain.cultural_offer.command;

import cultureapp.domain.core.validation.SelfValidating;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.cultural_offer.exception.SubscriptionAlreadyExistsException;
import cultureapp.domain.user.exception.RegularUserNotFoundException;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.Positive;

public interface SubscribeToCulturalOfferNewsUseCase {
    void subscribe(SubscribeToCulturalOfferNewsCommand command)
            throws RegularUserNotFoundException, CulturalOfferNotFoundException, SubscriptionAlreadyExistsException;

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
