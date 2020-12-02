package cultureapp.domain.cultural_offer.query;

import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.image.Image;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

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
        List<String> images;

        public static GetCulturalOfferByIdDTO of(CulturalOffer offer) {
            return new GetCulturalOfferByIdDTO(
                    offer.getId(),
                    offer.getName(),
                    offer.getDescription(),
                    offer.getLocation().getLongitude(),
                    offer.getLocation().getLatitude(),
                    offer.getSubcategory().getName(),
                    offer.getImages().stream().map(Image::getUrl).collect(Collectors.toList()));
        }
    }
}
