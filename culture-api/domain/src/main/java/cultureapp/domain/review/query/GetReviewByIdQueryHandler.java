package cultureapp.domain.review.query;

import cultureapp.domain.core.validation.SelfValidating;
import cultureapp.domain.image.Image;
import cultureapp.domain.reply.Reply;
import cultureapp.domain.review.Review;
import cultureapp.domain.review.exception.ReviewNotFoundException;
import cultureapp.domain.user.User;
import lombok.*;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public interface GetReviewByIdQueryHandler {
    GetReviewByIdDTO handleGetReview(GetReviewByIdQuery query) throws ReviewNotFoundException;

    @Value
    @EqualsAndHashCode(callSuper = false)
    class GetReviewByIdQuery extends SelfValidating<GetReviewByIdQuery> {
        @Positive
        Long id;

        @Positive
        Long culturalOfferId;

        public GetReviewByIdQuery(
                Long id,
                Long culturalOfferId
        ) {
            this.id = id;
            this.culturalOfferId = culturalOfferId;
            this.validateSelf();
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    class GetReviewByIdDTO {
        Long id;
        Long culturalOfferId;
        BigDecimal rating;
        AuthorDTO author;
        String comment;
        List<String> images;

        ReplyForReviewByIdDTO reply;

        public static GetReviewByIdDTO of(Review review) {
            return new GetReviewByIdDTO(
                    review.getId(),
                    review.getCulturalOffer().getId(),
                    review.getRating(),
                    AuthorDTO.of(review.getAuthor()),
                    review.getComment(),
                    mapImages(review.getImages()),
                    review.getReply() != null ? ReplyForReviewByIdDTO.of(review.getReply()) : null
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


        @AllArgsConstructor(access = AccessLevel.PRIVATE)
        @Getter
        private static class ReplyForReviewByIdDTO {
            private String comment;
            private AuthorDTO author;

            private static ReplyForReviewByIdDTO of(Reply reply) {
                return new ReplyForReviewByIdDTO(
                        reply.getComment(),
                        AuthorDTO.of(reply.getAdministrator())
                );
            }
        }
    }
}
