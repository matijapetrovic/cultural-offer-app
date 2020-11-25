package cultureapp.domain.cultural_offer.command;


import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;

import javax.validation.constraints.Positive;

public interface DeleteCulturalOfferUseCase {
    void deleteCulturalOffer(@Positive Long id) throws CulturalOfferNotFoundException;
}
