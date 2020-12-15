package cultureapp.domain.review.query;

import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.image.Image;
import cultureapp.domain.review.Review;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Slice;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

public interface GetReviewsQuery {
    Slice<GetReviewsQueryDTO> getReviewsDTO(@Positive Long culturalOfferId,
                                       @PositiveOrZero Integer page,
                                       @Positive Integer limit) throws CulturalOfferNotFoundException;

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    class GetReviewsQueryDTO {
        Long id;
        Long culturalOfferId;
        String comment;
        List<String> images;

        public static GetReviewsQueryDTO of(Review review) {
            return new GetReviewsQueryDTO(
                    review.getId(),
                    review.getCulturalOffer().getId(),
                    review.getComment(),
                    review.getImages()
                            .stream()
                            .map(Image::getUrl)
                            .collect(Collectors.toList())
            );
        }
    }
}
