package cultureapp.rest.reply;

import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.reply.command.AddReplyUseCase;
import cultureapp.domain.reply.exception.ReplyAlreadyExistException;
import cultureapp.domain.review.exception.ReviewNotFoundException;
import cultureapp.domain.user.exception.AdminNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/cultural-offers/{culturalOfferId}/reviews/{reviewId}/replies", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReplyController {
    private final AddReplyUseCase addReplyUseCase;

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> addReply(
            @PathVariable Long culturalOfferId,
            @PathVariable Long reviewId,
            @RequestBody ReplyRequest request
    ) throws AdminNotFoundException, ReviewNotFoundException, ReplyAlreadyExistException {
        AddReplyUseCase.AddReplyCommand command = new AddReplyUseCase.AddReplyCommand(
                culturalOfferId,
                reviewId,
                request.getComment()
        );
        addReplyUseCase.addReply(command);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
