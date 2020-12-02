package cultureapp.domain.news.query;

import cultureapp.domain.cultural_offer.Image;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.news.News;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Slice;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public interface GetNewsQuery {
    Slice<GetNewsDTO> getNews(Long offerId, Integer page, Integer limit) throws CulturalOfferNotFoundException;

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @Setter
    class GetNewsDTO {
        private Long id;
        private Long culturalOfferId;
        private String name;
        private LocalDateTime postedDate;
        private Long authorId;
        private String text;

//        private List<String> images;

        public static GetNewsDTO of(News news) {

            return new GetNewsDTO(
                    news.getId(),
                    news.getCulturalOffer().getId(),
                    news.getName(),
                    news.getPostedDate(),
                    news.getAuthor().getId(),
                    news.getText()
            );
        }
    }
}
