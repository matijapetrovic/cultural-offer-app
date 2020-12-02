package cultureapp.domain.review.query;

import cultureapp.domain.cultural_offer.Image;
import cultureapp.domain.review.Review;
import cultureapp.domain.review.exception.ReviewNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

public interface GetReviewByIdQuery {
    GetReviewByIdDTO getReview(@Positive Long id, @Positive Long culturalOfferId) throws ReviewNotFoundException;

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    class GetReviewByIdDTO {
        Long id;
        String comment;
        Long culturalOfferId;
        Long replyID;
        List<String> images;

        public static GetReviewByIdDTO of(Review review) {
            return new GetReviewByIdDTO(
                    review.getId(),
                    review.getComment(),
                    review.getCulturalOffer().getId(),
                    review.getReply().getId(),
                    review
                        .getImages()
                        .stream()
                        .map(Image::toString)
                        .collect(Collectors.toList())
            );
        }
    }
}
