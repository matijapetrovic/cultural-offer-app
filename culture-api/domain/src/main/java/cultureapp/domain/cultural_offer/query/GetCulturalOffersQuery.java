package cultureapp.domain.cultural_offer.query;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

public interface GetCulturalOffersQuery {
    List<GetCulturalOffersDTO> getCulturalOffers();

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    class GetCulturalOffersDTO {
        Long id;
        String name;
        String description;
        Double longitude;
        Double latitude;
    }
}
