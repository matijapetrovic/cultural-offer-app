package cultureapp.domain.news.command;

import cultureapp.domain.user.exception.AdminNotFoundException;
import cultureapp.domain.core.validation.SelfValidating;
import cultureapp.domain.core.validation.annotation.IdList;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.image.exception.ImageNotFoundException;
import cultureapp.domain.news.exception.NewsAlreadyExistException;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

public interface AddNewsUseCase {
    void addNews(AddNewsCommand command) throws CulturalOfferNotFoundException, AdminNotFoundException, NewsAlreadyExistException, ImageNotFoundException;

    @Value
    @EqualsAndHashCode(callSuper = false)
    class AddNewsCommand extends SelfValidating<AddNewsCommand> {
        @Positive
        Long culturalOfferID;

        @NotBlank
        String name;

        @NotNull
        LocalDateTime postedDate;

        @Positive
        Long authorID;

        @NotBlank
        String text;

        @IdList
        List<Long> images;

        public AddNewsCommand(
                Long culturalOfferID,
                String name,
                LocalDateTime postedDate,
                Long authorID,
                String text,
                List<Long> images
        ) {
            this.culturalOfferID = culturalOfferID;
            this.name = name;
            this.postedDate = postedDate;
            this.authorID = authorID;
            this.text = text;
            this.images = images;
            this.validateSelf();
        }
    }
}
