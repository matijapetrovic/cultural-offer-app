package cultureapp.domain.news.command;

import cultureapp.domain.administrator.exception.AdminNotFoundException;
import cultureapp.domain.core.validation.SelfValidating;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Date;
import java.util.List;

public interface AddNewsUseCase {
    void addNews(AddNewsCommand command) throws CulturalOfferNotFoundException, AdminNotFoundException;

    @Value
    @EqualsAndHashCode(callSuper = false)
    class AddNewsCommand extends SelfValidating<AddNewsCommand> {
        @Positive
        Long culturalOfferID;

        @NotBlank
        String name;

        @NotBlank
        Date postedDate;

        @NotBlank
        Long authorID;

        @NotBlank
        String text;

        // Base64 String encoded images
        List<String> images;

        public AddNewsCommand(
                Long culturalOfferID,
                String name,
                Date postedDate,
                Long authorID,
                String text,
                List<String> images
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
