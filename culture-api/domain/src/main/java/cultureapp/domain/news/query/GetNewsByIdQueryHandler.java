package cultureapp.domain.news.query;

import cultureapp.domain.core.validation.SelfValidating;
import cultureapp.domain.image.Image;
import cultureapp.domain.news.News;
import cultureapp.domain.news.exception.NewsNotFoundException;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public interface GetNewsByIdQueryHandler {
    GetNewsByIdDTO handleGetNewsById(GetNewsByIdQuery query) throws NewsNotFoundException;

    @Value
    @EqualsAndHashCode(callSuper = false)
    class GetNewsByIdQuery extends SelfValidating<GetNewsByIdQuery> {
        @NotNull
        @Positive
        Long id;

        @NotNull
        @Positive
        Long culturalOfferId;

        public GetNewsByIdQuery(
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
    @Setter
    class GetNewsByIdDTO {
        private Long id;
        private Long culturalOfferId;
        private String name;
        private String postedDate;
        private Long authorId;
        private String text;
        List<String> images;

        public static GetNewsByIdDTO of(News news) {

            return new GetNewsByIdDTO(
                    news.getId(),
                    news.getCulturalOffer().getId(),
                    news.getTitle(),
                    news.getPostedDate().toString().replace("T", " "),
                    news.getAuthor().getId(),
                    news.getText(),
                    news.getImages()
                            .stream()
                            .map(Image::getUrl)
                            .collect(Collectors.toList())
            );
        }
    }
}
