package cultureapp.domain.news.query;

import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.news.News;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public interface GetNewsQuery {
    Slice<GetNewsDTO> getNews(Long offerId, Integer page, Integer limit) throws CulturalOfferNotFoundException;

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @Setter
    class GetNewsDTO {
        private Long id;
        private Long culturalOfferId;
        private String title;
        private String postedDate;
        private Long authorId;
        private String text;

        private List<String> images;

        public static GetNewsDTO of(News news) {

            return new GetNewsDTO(
                    news.getId(),
                    news.getCulturalOffer().getId(),
                    news.getTitle(),
                    news.getPostedDate().toString().replace("T", " "),
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
