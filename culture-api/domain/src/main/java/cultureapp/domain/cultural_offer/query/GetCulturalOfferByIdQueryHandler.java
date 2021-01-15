package cultureapp.domain.cultural_offer.query;

import cultureapp.domain.core.validation.SelfValidating;
import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.image.Image;
import cultureapp.domain.user.exception.RegularUserNotFoundException;
import lombok.*;

import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

public interface GetCulturalOfferByIdQueryHandler {
    GetCulturalOfferByIdDTO handleGetCulturalOffer(GetCulturalOfferByIdQuery query) throws
            CulturalOfferNotFoundException,
            RegularUserNotFoundException;

    @Value
    @EqualsAndHashCode(callSuper = false)
    class GetCulturalOfferByIdQuery extends SelfValidating<GetCulturalOfferByIdQuery> {
        @Positive
        Long id;

        public GetCulturalOfferByIdQuery(Long id) {
            this.id = id;
            this.validateSelf();
        }
    }

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
