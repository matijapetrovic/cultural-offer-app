package cultureapp.domain.cultural_offer.exception;

public class CulturalOfferNotFoundException extends Exception {
    public CulturalOfferNotFoundException(Long culturalOfferId) {
        super(String.format("Cultural offer with id %d not found", culturalOfferId));
    }
}
