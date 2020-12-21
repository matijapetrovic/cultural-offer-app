package cultureapp.rest.review;


import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.image.exception.ImageNotFoundException;
import cultureapp.domain.user.exception.RegularUserNotFoundException;
import cultureapp.domain.review.command.AddReviewUseCase;
import cultureapp.domain.review.command.DeleteReviewUseCase;
import cultureapp.domain.review.exception.ReviewNotFoundException;
import cultureapp.domain.review.query.GetReviewByIdQuery;
import cultureapp.domain.review.query.GetReviewsForOfferQuery;
import cultureapp.rest.core.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/cultural-offers/{culturalOfferId}/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReviewController {
    private final AddReviewUseCase addReviewUseCase;
    private final GetReviewByIdQuery getReviewByIdQuery;
    private final GetReviewsForOfferQuery getReviewsForOfferQuery;
    private final DeleteReviewUseCase deleteReviewUseCase;

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Void> addReview(@PathVariable Long culturalOfferId,
                          @RequestBody ReviewRequest request) throws CulturalOfferNotFoundException, ImageNotFoundException, RegularUserNotFoundException {
        AddReviewUseCase.AddReviewCommand command =
                new AddReviewUseCase.AddReviewCommand(
                        culturalOfferId,
                        request.getComment(),
                        request.getRating(),
                        request.getImages()
                );
        addReviewUseCase.addReview(command);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "", params = { "page", "limit" })
    public ResponseEntity<PaginatedResponse<GetReviewsForOfferQuery.GetReviewsForOfferQueryDTO>> getReviews(
            @PathVariable Long culturalOfferId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit,
            UriComponentsBuilder uriBuilder
    ) throws CulturalOfferNotFoundException {
        Slice<GetReviewsForOfferQuery.GetReviewsForOfferQueryDTO> result =
                getReviewsForOfferQuery.getReviewsForOffer(culturalOfferId, page, limit);
        String resourceUri = String.format("/api/cultural-offers/%d/reviews", culturalOfferId);
        uriBuilder.path(resourceUri);
        return ResponseEntity.ok((PaginatedResponse.of(result, uriBuilder)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetReviewByIdQuery.GetReviewByIdDTO> getReview(@PathVariable Long culturalOfferId,
                                                                         @PathVariable Long id) throws ReviewNotFoundException {
        return ResponseEntity.ok(getReviewByIdQuery.getReview(id, culturalOfferId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long culturalOfferId,
                             @PathVariable Long id) throws CulturalOfferNotFoundException, ReviewNotFoundException {
        deleteReviewUseCase.deleteReviewByCulturalOfferId(id, culturalOfferId);
        return ResponseEntity.noContent().build();
    }
}
