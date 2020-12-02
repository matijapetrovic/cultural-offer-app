package cultureapp.domain.review;

import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.CulturalOfferRepository;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.review.command.AddReviewUseCase;
import cultureapp.domain.review.command.DeleteReviewUseCase;
import cultureapp.domain.review.exception.ReviewNotFoundException;
import cultureapp.domain.review.query.GetReviewByIdQuery;
import cultureapp.domain.review.query.GetReviewsQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RequiredArgsConstructor
@Service
public class ReviewService implements
        AddReviewUseCase,
        DeleteReviewUseCase,
        GetReviewByIdQuery,
        GetReviewsQuery
{
    private final ReviewRepository reviewRepository;
    private final CulturalOfferRepository culturalOfferRepository;

    @Override
    public void addReview(AddReviewCommand command) throws CulturalOfferNotFoundException {
        CulturalOffer culturalOffer =
                        culturalOfferRepository.findById(command.getCulturalOfferId())
                        .orElseThrow(() -> new CulturalOfferNotFoundException(command.getCulturalOfferId()));
        Review review = Review.of(
                culturalOffer,
                command.getComment(),
                command.getRating(),
                false,
                null);
        // TODO pogledati sta za slike
        reviewRepository.save(review);
    }

    @Override
    public void deleteReviewByCulturalOfferId(@Positive Long id, @Positive Long culturalOfferId) throws ReviewNotFoundException {
        Review review =
                reviewRepository.findByIdAndCulturalOfferIdAndArchivedFalse(id, culturalOfferId)
                .orElseThrow(() -> new ReviewNotFoundException(id, culturalOfferId));
        review.archive();
        reviewRepository.save(review);
        // TODO obrisi sve reply-eve koji imaju id ovog revieva
    }

    @Override
    public GetReviewByIdDTO getReview(@Positive Long id, @Positive Long culturalOfferId) throws ReviewNotFoundException {
        Review review =
                reviewRepository.findByIdAndCulturalOfferIdAndArchivedFalse(id, culturalOfferId)
                .orElseThrow(() -> new ReviewNotFoundException(id, culturalOfferId));
        return GetReviewByIdDTO.of(review);
    }


    @Override
    public Slice<GetReviewsQueryDTO> getReviewsDTO(@Positive Long culturalOfferId, @PositiveOrZero Integer page, @Positive Integer limit) throws CulturalOfferNotFoundException {
        CulturalOffer culturalOffer =
                culturalOfferRepository.findById(culturalOfferId)
                        .orElseThrow(() -> new CulturalOfferNotFoundException(culturalOfferId));
        // TODO sortirati po datumu
        Pageable pageRequest = PageRequest.of(page, limit);


        Slice<Review> reviews = reviewRepository
                .findAllByCulturalOfferIdAndArchivedFalse(culturalOfferId, pageRequest);

        return reviews.map(GetReviewsQueryDTO::of);
    }
}
