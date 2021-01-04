package cultureapp.domain.review.query;

import cultureapp.domain.core.validation.SelfValidating;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.image.Image;
import cultureapp.domain.review.Review;
import cultureapp.domain.user.User;
import lombok.*;
import org.springframework.data.domain.Slice;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public interface GetReviewsForOfferQueryHandler {
    Slice<GetReviewsForOfferQueryDTO> handleGetReviews(GetReviewsForOfferQuery query) throws
            CulturalOfferNotFoundException;

    @Value
    @EqualsAndHashCode(callSuper = false)
    class GetReviewsForOfferQuery extends SelfValidating<GetReviewsForOfferQuery> {
        @Positive
        Long culturalOfferId;

        @PositiveOrZero
        Integer page;

        @Positive
        Integer limit;

        public GetReviewsForOfferQuery(
                Long culturalOfferId,
                Integer page,
                Integer limit
        ) {
            this.culturalOfferId = culturalOfferId;
            this.page = page;
            this.limit = limit;
            this.validateSelf();
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    class GetReviewsForOfferQueryDTO {
        Long id;
        Long culturalOfferId;
        BigDecimal rating;
        AuthorDTO author;
        String comment;
        List<String> images;

        public static GetReviewsForOfferQueryDTO of(Review review) {
            return new GetReviewsForOfferQueryDTO(
                    review.getId(),
                    review.getCulturalOffer().getId(),
                    review.getRating(),
                    AuthorDTO.of(review.getAuthor()),
                    review.getComment(),
                    mapImages(review.getImages())
            );
        }

        private static List<String> mapImages(List<Image> images) {
            return images.stream()
                    .map(Image::getUrl)
                    .collect(Collectors.toList());
        }

        @AllArgsConstructor(access = AccessLevel.PRIVATE)
        @Getter
        private static class AuthorDTO {
            private Long id;
            private String firstName;
            private String lastName;

            private static AuthorDTO of(User author) {
                return new AuthorDTO(
                        author.getId(),
                        author.getFirstName(),
                        author.getLastName());
            }
        }
    }
}
