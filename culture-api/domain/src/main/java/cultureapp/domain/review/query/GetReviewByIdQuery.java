package cultureapp.domain.review.query;

import cultureapp.domain.image.Image;
import cultureapp.domain.review.Review;
import cultureapp.domain.review.exception.ReviewNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public interface GetReviewByIdQuery {
    GetReviewByIdDTO getReview(@Positive Long id, @Positive Long culturalOfferId) throws ReviewNotFoundException;

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    class GetReviewByIdDTO {
        Long id;
        Long culturalOfferId;
        BigDecimal rating;
        String comment;
        List<String> images;

        public static GetReviewByIdDTO of(Review review) {
            return new GetReviewByIdDTO(
                    review.getId(),
                    review.getCulturalOffer().getId(),
                    review.getRating(),
                    review.getComment(),
                    mapImages(review.getImages())
            );
        }

        private static List<String> mapImages(List<Image> images) {
            return images.stream()
                    .map(Image::getUrl)
                    .collect(Collectors.toList());
        }
    }
}
