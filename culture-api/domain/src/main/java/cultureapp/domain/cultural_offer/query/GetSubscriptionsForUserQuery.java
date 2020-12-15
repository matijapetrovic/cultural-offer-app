package cultureapp.domain.cultural_offer.query;

import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.image.Image;
import cultureapp.domain.user.exception.RegularUserNotFoundException;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

public interface GetSubscriptionsForUserQuery {
    List<GetSubscriptionsForUserDTO> getSubscriptions(
            @Positive Long categoryId,
            @Positive Long subcategoryId) throws RegularUserNotFoundException, SubcategoryNotFoundException;

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    class GetSubscriptionsForUserDTO {
        Long id;
        String name;
        String description;
        List<String> images;

        public static GetSubscriptionsForUserDTO of(CulturalOffer offer) {
            return new GetSubscriptionsForUserDTO(
                    offer.getId(),
                    offer.getName(),
                    offer.getDescription(),
                    offer.getImages().stream().map(Image::getUrl).collect(Collectors.toList()));
        }
    }
}
