package cultureapp.domain.review.command;

import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.review.exception.ReviewNotFoundException;

import javax.validation.constraints.Positive;

public interface DeleteReviewUseCase {
    void deleteReviewByCulturalOfferId(@Positive Long id, @Positive Long culturalOfferId) throws CulturalOfferNotFoundException, ReviewNotFoundException;
}
