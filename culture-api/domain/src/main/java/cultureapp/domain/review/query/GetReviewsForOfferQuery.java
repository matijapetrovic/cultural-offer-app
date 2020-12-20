package cultureapp.domain.review.query;

import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.image.Image;
import cultureapp.domain.news.query.GetNewsForOfferQuery;
import cultureapp.domain.review.Review;
import cultureapp.domain.user.Administrator;
import cultureapp.domain.user.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Slice;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public interface GetReviewsForOfferQuery {
    Slice<GetReviewsForOfferQueryDTO> getReviewsForOffer(
            @Positive Long culturalOfferId,
            @PositiveOrZero Integer page,
            @Positive Integer limit) throws CulturalOfferNotFoundException;

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
