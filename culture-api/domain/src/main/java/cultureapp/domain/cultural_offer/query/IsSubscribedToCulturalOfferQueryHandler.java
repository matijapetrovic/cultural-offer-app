package cultureapp.domain.cultural_offer.query;

import cultureapp.domain.core.validation.SelfValidating;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.user.exception.RegularUserNotFoundException;
import lombok.*;

import javax.validation.constraints.Positive;

public interface IsSubscribedToCulturalOfferQueryHandler {
    boolean isSubscribed(IsSubscribedToCulturalOfferQuery query)
            throws CulturalOfferNotFoundException, RegularUserNotFoundException;

    @Value
    @EqualsAndHashCode(callSuper = false)
    class IsSubscribedToCulturalOfferQuery extends SelfValidating<IsSubscribedToCulturalOfferQuery> {
        @Positive
        Long id;

        public IsSubscribedToCulturalOfferQuery(Long id) {
            this.id = id;
            this.validateSelf();
        }
    }

}
