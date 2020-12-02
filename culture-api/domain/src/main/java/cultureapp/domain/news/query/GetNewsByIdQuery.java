package cultureapp.domain.news.query;

import cultureapp.domain.news.News;
import cultureapp.domain.news.exception.NewsNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public interface GetNewsByIdQuery {
    GetNewsByIdDTO getNewsById(@Positive Long id, @Positive Long culturalOfferId) throws NewsNotFoundException;

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @Setter
    class GetNewsByIdDTO {
        private Long id;
        private Long culturalOfferId;
        private String name;
        private LocalDateTime postedDate;
        private Long authorId;
        private String text;
        List<String> images;

        public static GetNewsByIdDTO of(News news) {

            return new GetNewsByIdDTO(
                    news.getId(),
                    news.getCulturalOffer().getId(),
                    news.getName(),
                    news.getPostedDate(),
                    news.getAuthor().getId(),
                    news.getText(),
                    news.getImages()
                            .stream()
                            .map(image -> image.getUrl())
                            .collect(Collectors.toList())
            );
        }
    }
}
