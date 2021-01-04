package cultureapp.domain.news.query;

import cultureapp.domain.core.validation.SelfValidating;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.image.Image;
import cultureapp.domain.news.News;
import cultureapp.domain.user.Administrator;
import lombok.*;
import org.springframework.data.domain.Slice;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public interface GetNewsForOfferQueryHandler {
    Slice<GetNewsForOfferDTO> handleGetNewsForOffer(GetNewsForOfferQuery query) throws CulturalOfferNotFoundException;

    @Value
    @EqualsAndHashCode(callSuper = false)
    class GetNewsForOfferQuery extends SelfValidating<GetNewsForOfferQuery> {
        @NotNull
        @Positive
        Long culturalOfferId;

        @NotNull
        @PositiveOrZero
        Integer page;

        @NotNull
        @Positive
        Integer limit;

        public GetNewsForOfferQuery(
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
    @Setter
    class GetNewsForOfferDTO {
        private Long id;
        private Long culturalOfferId;
        private String title;
        private LocalDateTime postedDate;
        private AuthorDTO author;
        private String text;

        private List<String> images;

        public static GetNewsForOfferDTO of(News news) {
            return new GetNewsForOfferDTO(
                    news.getId(),
                    news.getCulturalOffer().getId(),
                    news.getTitle(),
                    news.getPostedDate(),
                    AuthorDTO.of(news.getAuthor()),
                    news.getText(),
                    news.getImages()
                            .stream()
                            .map(Image::getUrl)
                            .collect(Collectors.toList())
            );
        }

        @AllArgsConstructor(access = AccessLevel.PRIVATE)
        @Getter
        private static class AuthorDTO {
            private Long id;
            private String firstName;
            private String lastName;

            private static AuthorDTO of(Administrator author) {
                return new AuthorDTO(
                        author.getId(),
                        author.getFirstName(),
                        author.getLastName());
            }
        }
    }
}
