package cultureapp.domain.cultural_offer.query;

import cultureapp.domain.core.validation.SelfValidating;
import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.image.Image;
import lombok.*;
import org.springframework.data.domain.Slice;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

public interface GetCulturalOffersQueryHandler {
    Slice<GetCulturalOffersDTO> handleGetCulturalOffers(GetCulturalOffersQuery query);

    @Value
    @EqualsAndHashCode(callSuper = false)
    class GetCulturalOffersQuery extends SelfValidating<GetCulturalOffersQuery> {
        @PositiveOrZero
        Integer page;

        @Positive
        Integer limit;

        public GetCulturalOffersQuery(
                Integer page,
                Integer limit
        ) {
            this.page = page;
            this.limit = limit;
            this.validateSelf();
        }
    }

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
