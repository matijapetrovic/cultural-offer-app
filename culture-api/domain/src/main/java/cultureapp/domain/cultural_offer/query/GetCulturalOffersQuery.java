package cultureapp.domain.cultural_offer.query;

import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.image.Image;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

public interface GetCulturalOffersQuery {
    Slice<GetCulturalOffersDTO> getCulturalOffers(Integer page, Integer limit);

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    class GetCulturalOffersDTO {
        Long id;
        String name;
        String description;
        Double longitude;
        Double latitude;
        String subcategory;
        List<String> images;

        public static GetCulturalOffersDTO of(CulturalOffer offer) {
            return new GetCulturalOffersDTO(
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
