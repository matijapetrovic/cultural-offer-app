package cultureapp.domain.reply.command;

import cultureapp.domain.core.validation.SelfValidating;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.reply.exception.ReplyAlreadyExistException;
import cultureapp.domain.review.exception.ReviewNotFoundException;
import cultureapp.domain.user.exception.AdminNotFoundException;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public interface AddReplyUseCase {
    void addReply(AddReplyCommand command) throws ReviewNotFoundException, AdminNotFoundException, ReplyAlreadyExistException;


    @Value
    @EqualsAndHashCode(callSuper = false)
    class AddReplyCommand extends SelfValidating<AddReplyCommand> {
        @NotNull
        @Positive
        Long culturalOfferId;

        @NotNull
        @Positive
        Long reviewId;

        @NotBlank
        String comment;

        public AddReplyCommand(Long culturalOfferId, Long reviewId, String comment) {
            this.culturalOfferId = culturalOfferId;
            this.reviewId = reviewId;
            this.comment = comment;
            this.validateSelf();
        }
    }
}
