package cultureapp.domain.news.command;

import cultureapp.domain.user.exception.AdminNotFoundException;
import cultureapp.domain.core.validation.SelfValidating;
import cultureapp.domain.core.validation.annotation.IdList;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.image.exception.ImageNotFoundException;
import cultureapp.domain.news.exception.NewsAlreadyExistException;
import cultureapp.domain.news.exception.NewsNotFoundException;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

public interface UpdateNewsUseCase {
    void updateNews(UpdateNewsCommand command)
            throws NewsNotFoundException,
            AdminNotFoundException,
            CulturalOfferNotFoundException, NewsAlreadyExistException, ImageNotFoundException;

    @Value
    @EqualsAndHashCode(callSuper = true)
    class UpdateNewsCommand extends SelfValidating<UpdateNewsCommand> {
        @NotNull
        @Positive
        Long id;

        @NotNull
        @Positive
        Long culturalOfferID;

        @NotNull
        @NotBlank
        String name;

        @NotNull
        LocalDateTime postedDate;

        @NotNull
        @Positive
        Long authorID;

        @NotNull
        @NotBlank
        String text;

        @IdList
        List<Long> images;

        public UpdateNewsCommand(Long id, Long culturalOfferID, String name,
                                 LocalDateTime postedDate, Long authorID, String text, List<Long> images)
        {
            this.id = id;
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
