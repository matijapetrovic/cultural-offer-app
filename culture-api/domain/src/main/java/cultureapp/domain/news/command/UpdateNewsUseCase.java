package cultureapp.domain.news.command;

import cultureapp.domain.administrator.exception.AdminNotFoundException;
import cultureapp.domain.core.validation.SelfValidating;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.news.exception.NewsNotFoundException;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.Date;

public interface UpdateNewsUseCase {
    void updateNews(UpdateNewsCommand command)
            throws NewsNotFoundException,
            AdminNotFoundException,
            CulturalOfferNotFoundException;

    @Value
    @EqualsAndHashCode(callSuper = true)
    class UpdateNewsCommand extends SelfValidating<UpdateNewsCommand> {
        @Positive
        Long id;

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

        public UpdateNewsCommand(Long id, Long culturalOfferID, String name,
                                 LocalDateTime postedDate, Long authorID, String text)
        {
            this.id = id;
            this.culturalOfferID = culturalOfferID;
            this.name = name;
            this.postedDate = postedDate;
            this.authorID = authorID;
            this.text = text;
            this.validateSelf();
        }
    }
}
