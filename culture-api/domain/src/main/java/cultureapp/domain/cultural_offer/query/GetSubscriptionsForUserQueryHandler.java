package cultureapp.domain.cultural_offer.query;

import cultureapp.domain.core.validation.SelfValidating;
import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.image.Image;
import cultureapp.domain.user.exception.RegularUserNotFoundException;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import lombok.*;
import org.springframework.data.domain.Slice;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

public interface GetSubscriptionsForUserQueryHandler {
    Slice<GetSubscriptionsForUserDTO> handleGetSubscriptions(GetSubscriptionsForUserQuery query) throws
            RegularUserNotFoundException,
            SubcategoryNotFoundException;

    @Value
    @EqualsAndHashCode(callSuper = false)
    class GetSubscriptionsForUserQuery extends SelfValidating<GetSubscriptionsForUserQuery> {
        @Positive
        Long categoryId;

        @Positive
        Long subcategoryId;

        @PositiveOrZero
        @NotNull
        Long page;

        @Positive
        @NotNull
        Long limit;

        public GetSubscriptionsForUserQuery(
                Long categoryId,
                Long subcategoryId,
                Long page,
                Long limit
        ) {
            this.categoryId = categoryId;
            this.subcategoryId = subcategoryId;
            this.page = page;
            this.limit = limit;
            this.validateSelf();
        }
    }

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
