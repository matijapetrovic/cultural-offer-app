package cultureapp.domain.review;

import cultureapp.domain.account.Account;
import cultureapp.domain.core.AuthenticationService;
import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.CulturalOfferRepository;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.core.DateTimeProvider;
import cultureapp.domain.image.Image;
import cultureapp.domain.image.ImageRepository;
import cultureapp.domain.image.exception.ImageNotFoundException;
import cultureapp.domain.user.RegularUser;
import cultureapp.domain.user.RegularUserRepository;
import cultureapp.domain.user.exception.RegularUserNotFoundException;
import cultureapp.domain.review.command.AddReviewUseCase;
import cultureapp.domain.review.command.DeleteReviewUseCase;
import cultureapp.domain.review.exception.ReviewNotFoundException;
import cultureapp.domain.review.query.GetReviewByIdQueryHandler;
import cultureapp.domain.review.query.GetReviewsForOfferQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService implements
        AddReviewUseCase,
        DeleteReviewUseCase,
        GetReviewByIdQueryHandler,
        GetReviewsForOfferQueryHandler {
    private final ReviewRepository reviewRepository;
    private final ImageRepository imageRepository;
    private final CulturalOfferRepository culturalOfferRepository;
    private final DateTimeProvider dateTimeProvider;
    private final AuthenticationService authenticationService;
    private final RegularUserRepository regularUserRepository;

    @Override
    public void addReview(AddReviewCommand command) throws CulturalOfferNotFoundException, ImageNotFoundException, RegularUserNotFoundException {
        Account account = authenticationService.getAuthenticated();
        RegularUser user = regularUserRepository
                .findByAccountId(account.getId())
                .orElseThrow(() -> new RegularUserNotFoundException(account.getEmail()));
        CulturalOffer culturalOffer =
                        culturalOfferRepository.findByIdAndArchivedFalse(command.getCulturalOfferId())
                        .orElseThrow(() -> new CulturalOfferNotFoundException(command.getCulturalOfferId()));
        List<Image> images = loadImages(command.getImages());
        LocalDateTime date = dateTimeProvider.now();
        Review review = Review.of(
                culturalOffer,
                command.getComment(),
                command.getRating(),
                false,
                images,
                user,
                date);
        reviewRepository.save(review);
    }

    @Override
    public void deleteReviewByCulturalOfferId(@Positive Long id, @Positive Long culturalOfferId) throws ReviewNotFoundException {
        Review review =
                reviewRepository.findByIdAndCulturalOfferIdAndArchivedFalse(id, culturalOfferId)
                .orElseThrow(() -> new ReviewNotFoundException(id, culturalOfferId));
        review.archive();
        reviewRepository.save(review);
    }

    @Override
    public GetReviewByIdDTO handleGetReview(GetReviewByIdQuery query) throws ReviewNotFoundException {
        Review review =
                reviewRepository.findByIdAndCulturalOfferIdAndArchivedFalse(query.getId(), query.getCulturalOfferId())
                .orElseThrow(() -> new ReviewNotFoundException(query.getId(), query.getCulturalOfferId()));
        return GetReviewByIdDTO.of(review);
    }

    public Slice<GetReviewsForOfferQueryDTO> handleGetReviews(GetReviewsForOfferQuery query) throws
            CulturalOfferNotFoundException {
        CulturalOffer culturalOffer =
                culturalOfferRepository.findByIdAndArchivedFalse(query.getCulturalOfferId())
                        .orElseThrow(() -> new CulturalOfferNotFoundException(query.getCulturalOfferId()));
        Pageable pageRequest = PageRequest.of(query.getPage(), query.getLimit(), Sort.by(Sort.Direction.DESC, "date"));

        Slice<Review> reviews = reviewRepository
                .findAllByCulturalOfferIdAndArchivedFalse(query.getCulturalOfferId(), pageRequest);

        return reviews.map(GetReviewsForOfferQueryDTO::of);
    }


    private List<Image> loadImages(List<Long> imageIds) throws ImageNotFoundException {
        List<Image> images = new ArrayList<>();
        for (Long imageId : imageIds) {
            Image image = imageRepository.findById(imageId)
                    .orElseThrow(() -> new ImageNotFoundException(imageId));
            images.add(image);
        }
        return images;
    }
}
