package cultureapp.domain.cultural_offer.query;

import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

public interface GetCulturalOfferByIdQuery {
    GetCulturalOfferByIdDTO getCulturalOffer(Long id) throws CulturalOfferNotFoundException;

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    class GetCulturalOfferByIdDTO {
        Long id;
        String name;
        String description;
        Double longitude;
        Double latitude;
        String subcategory;

        public static GetCulturalOfferByIdDTO of(CulturalOffer offer) {
            return new GetCulturalOfferByIdDTO(
                    offer.getId(),
                    offer.getName(),
                    offer.getDescription(),
                    offer.getLocation().getLongitude(),
                    offer.getLocation().getLatitude(),
                    offer.getSubcategory().getName()
            );
        }
    }
}
