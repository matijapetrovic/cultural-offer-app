package cultureapp.domain.cultural_offer.query;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

public interface GetCulturalOfferByIdQuery {
    GetCulturalOfferByIdDTO getCulturalOffer(Long id);

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    class GetCulturalOfferByIdDTO {
        Long id;
        String name;
        String description;
        Double longitude;
        Double latitude;
    }
}
