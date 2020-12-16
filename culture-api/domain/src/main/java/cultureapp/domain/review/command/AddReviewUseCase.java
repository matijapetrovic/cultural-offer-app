package cultureapp.domain.review.command;

import cultureapp.domain.core.validation.SelfValidating;
import cultureapp.domain.core.validation.annotation.IdList;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.image.exception.ImageNotFoundException;
import cultureapp.domain.user.exception.RegularUserNotFoundException;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;

public interface AddReviewUseCase {
    void addReview(AddReviewCommand command) throws CulturalOfferNotFoundException, ImageNotFoundException, RegularUserNotFoundException;

    @Value
    @EqualsAndHashCode(callSuper = false)
    class AddReviewCommand extends SelfValidating<AddReviewUseCase.AddReviewCommand> {
        @Positive
        Long culturalOfferId;
        @NotBlank
        String comment;
        @PositiveOrZero
        BigDecimal rating;
        @IdList
        List<Long> images;

        public AddReviewCommand(Long culturalOfferId, String comment, BigDecimal rating,List<Long> images) {
            this.culturalOfferId = culturalOfferId;
            this.comment = comment;
            this.rating = rating;
            this.images = images;
            this.validateSelf();
        }
    }
}
