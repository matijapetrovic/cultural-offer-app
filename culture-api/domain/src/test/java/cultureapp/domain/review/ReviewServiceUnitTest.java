package cultureapp.domain.review;

import static cultureapp.common.ReviewTestData.*;
import static cultureapp.common.CulturalOfferTestData.*;
import static cultureapp.common.CategoryTestData.*;
import static cultureapp.common.SubcategoryTestData.*;
import static cultureapp.common.RegularUserTestData.*;
import static cultureapp.common.AuthenticationTestData.*;
import static cultureapp.common.ImageTestData.*;

import cultureapp.domain.account.Account;
import cultureapp.domain.authority.Authority;
import cultureapp.domain.category.Category;
import cultureapp.domain.core.AuthenticationService;
import cultureapp.domain.core.DateTimeProvider;
import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.CulturalOfferRepository;
import cultureapp.domain.cultural_offer.Location;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.image.Image;
import cultureapp.domain.image.ImageRepository;
import cultureapp.domain.image.exception.ImageNotFoundException;
import cultureapp.domain.review.command.AddReviewUseCase;
import cultureapp.domain.review.exception.ReviewNotFoundException;
import cultureapp.domain.review.query.GetReviewByIdQuery;
import cultureapp.domain.review.query.GetReviewsForOfferQuery;
import cultureapp.domain.subcategory.Subcategory;
import cultureapp.domain.user.RegularUser;
import cultureapp.domain.user.RegularUserRepository;
import cultureapp.domain.user.exception.RegularUserNotFoundException;
import org.springframework.data.domain.Slice;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.SliceImpl;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.junit.Assert.*;

public class ReviewServiceUnitTest {

    private final ReviewRepository reviewRepository =
            Mockito.mock(ReviewRepository.class);

    private final CulturalOfferRepository culturalOfferRepository =
            Mockito.mock(CulturalOfferRepository.class);

    private final ImageRepository imageRepository =
            Mockito.mock(ImageRepository.class);

    private final AuthenticationService authenticationService =
            Mockito.mock(AuthenticationService.class);

    private final RegularUserRepository regularUserRepository =
            Mockito.mock(RegularUserRepository.class);

    private final DateTimeProvider dateTimeProvider =
            Mockito.mock(DateTimeProvider.class);

    private final ReviewService reviewService =
            new ReviewService(
                    reviewRepository,
                    imageRepository,
                    culturalOfferRepository,
                    dateTimeProvider,
                    authenticationService,
                    regularUserRepository);

    private Category validCategory;
    private Subcategory validSubcategory;
    private Location validLocation;
    private CulturalOffer validCulturalOffer;
    private RegularUser validRegularUser;
    private List<Long> imageIds;
    private List<Image> images;

    @Before
    public void setUp() {
        imageIds = List.of(1L, 2L);
        images = List.of(Image.of("path1"), Image.of("path2"));
        validCategory = Category.withId(EXISTING_CULTURAL_OFFER_ID, VALID_CATEGORY_NAME);
        validSubcategory = Subcategory.of(validCategory, VALID_SUBCATEGORY_NAME);
        validRegularUser = RegularUser.of(
                VALID_REGULAR_USER_FIRST_NAME,
                VALID_REGULAR_USER_LAST_NAME,
                Account.of(
                        EXISTING_REGULAR_USER_EMAIL,
                        EXISTING_REGULAR_USER_PASSWORD,
                        true,
                        Set.of(Authority.of("REGULAR_USER")))
        );
        validLocation = Location.of(
                VALID_CULTURAL_OFFER_LONGITUDE,
                VALID_CULTURAL_OFFER_LATITUDE,
                VALID_CULTURAL_OFFER_ADDRESS);
        validCulturalOffer = CulturalOffer.withId(
                EXISTING_CULTURAL_OFFER_ID,
                VALID_CULTURAL_OFFER_NAME,
                VALID_CULTURAL_OFFER_DESCRIPTION,
                validLocation,
                images,
                Set.of(validRegularUser),
                validSubcategory);
    }


    /*
    -----------------------
    POST
    -----------------------
    */

    @Test
    public void givenAddReviewCommandIsValidThenAddReviewWillSucceed() throws CulturalOfferNotFoundException, RegularUserNotFoundException, ImageNotFoundException {
        given(authenticationService.getAuthenticated())
                .willReturn(Account.withId(
                        EXISTING_REGULAR_USER_ACCOUNT_ID,
                        EXISTING_REGULAR_USER_EMAIL,
                        EXISTING_REGULAR_USER_PASSWORD,
                        true,
                        Set.of(Authority.of("ROLE_USER"))));
        given(regularUserRepository.findByAccountId(EXISTING_REGULAR_USER_ACCOUNT_ID))
                .willReturn(Optional.of(validRegularUser));
        given(culturalOfferRepository.findByIdAndArchivedFalse(EXISTING_CULTURAL_OFFER_ID))
                .willReturn(Optional.of(validCulturalOffer));
        given(imageRepository.findById(EXISTING_IMAGE_ID_1)).willReturn(Optional.of(Image.of("path1")));
        given(imageRepository.findById(EXISTING_IMAGE_ID_2)).willReturn(Optional.of(Image.of("path2")));

        AddReviewUseCase.AddReviewCommand command = new AddReviewUseCase.AddReviewCommand(
                EXISTING_CULTURAL_OFFER_ID,
                VALID_REVIEW_COMMENT,
                VALID_REVIEW_RATING,
                imageIds);
        reviewService.addReview(command);

        then(reviewRepository)
                .should()
                .save(notNull());
    }

    @Test(expected = CulturalOfferNotFoundException.class)
    public void givenCulturalOfferDoesntExistThenAddReviewWillFail() throws CulturalOfferNotFoundException, RegularUserNotFoundException, ImageNotFoundException {
        given(authenticationService.getAuthenticated())
                .willReturn(Account.withId(
                        EXISTING_REGULAR_USER_ACCOUNT_ID,
                        EXISTING_REGULAR_USER_EMAIL,
                        EXISTING_REGULAR_USER_PASSWORD,
                        true,
                        Set.of(Authority.of("ROLE_USER"))));
        given(regularUserRepository.findByAccountId(EXISTING_REGULAR_USER_ACCOUNT_ID))
                .willReturn(Optional.of(validRegularUser));
        given(culturalOfferRepository.findByIdAndArchivedFalse(EXISTING_CULTURAL_OFFER_ID))
                .willReturn(Optional.of(validCulturalOffer));
        given(culturalOfferRepository.findByIdAndArchivedFalse(VALID_CULTURAL_OFFER_ID))
                .willReturn(Optional.empty());

        AddReviewUseCase.AddReviewCommand command = new AddReviewUseCase.AddReviewCommand(
                NON_EXISTING_CULTURAL_OFFER_ID,
                VALID_REVIEW_COMMENT,
                VALID_REVIEW_RATING,
                imageIds);

        reviewService.addReview(command);
    }

    @Test(expected = ConstraintViolationException.class)
    public void givenCulturalOfferIdIsInvalidThenCreateReviewCommandWillFail() {
        new AddReviewUseCase.AddReviewCommand(
                INVALID_CULTURAL_OFFER_ID,
                VALID_REVIEW_COMMENT,
                VALID_REVIEW_RATING,
                imageIds);
    }

    /*
    -----------------------
    GET
    -----------------------
    */

    @Test(expected = CulturalOfferNotFoundException.class)
    public void givenCulturalOfferDoesntExistThenGetReviewsWillFail() throws CulturalOfferNotFoundException {
        given(culturalOfferRepository.findByIdAndArchivedFalse(VALID_CULTURAL_OFFER_ID))
                .willReturn(Optional.empty());
        reviewService.getReviewsForOffer(VALID_CULTURAL_OFFER_ID, 0, 2);
    }

    @Test(expected = CulturalOfferNotFoundException.class)
    public void givenCulturalOfferIdInvalidThenGetReviewsWillFail() throws CulturalOfferNotFoundException {
        reviewService.getReviewsForOffer(INVALID_CULTURAL_OFFER_ID, 0, 2);
    }


    @Test
    public void givenCulturalOfferIdIsValidAndPageIsFirstThenGetReviewsWillReturnNonEmpty() throws CulturalOfferNotFoundException {
        List<Review> reviews = List.of(
                Review.of(
                        validCulturalOffer,
                        VALID_REVIEW_COMMENT,
                        VALID_REVIEW_RATING,
                        false,
                        images,
                        validRegularUser,
                        dateTimeProvider.now()),
                Review.of(
                        validCulturalOffer,
                        VALID_REVIEW_COMMENT,
                        VALID_REVIEW_RATING,
                        false,
                        images,
                        validRegularUser,
                        dateTimeProvider.now()));
        Slice<Review> slice = new SliceImpl<>(reviews);

        given(culturalOfferRepository.findByIdAndArchivedFalse(EXISTING_CULTURAL_OFFER_ID)).willReturn(Optional.of(validCulturalOffer));
        given(reviewRepository.findAllByCulturalOfferIdAndArchivedFalse(notNull(), notNull())).willReturn(slice);

        Slice<GetReviewsForOfferQuery.GetReviewsForOfferQueryDTO> result =
                reviewService.getReviewsForOffer(EXISTING_CULTURAL_OFFER_ID, 0, 2);

        then(reviewRepository)
                .should()
                .findAllByCulturalOfferIdAndArchivedFalse(notNull(), notNull());

        assertEquals(slice.getNumberOfElements(), result.getNumberOfElements());
    }

    /*
    -----------------------
    GET SINGLE
    -----------------------
    */

    @Test
    public void getCulturalOfferExistsThenReviewWillSucceed() throws ReviewNotFoundException {
        Review review = Review.withId(
                EXISTING_REVIEW_ID_FOR_CULTURAL_OFFER_ID_1,
                validCulturalOffer,
                VALID_REVIEW_COMMENT,
                VALID_REVIEW_RATING,
                false,
                images,
                validRegularUser,
                dateTimeProvider.now());
        given(reviewRepository.findByIdAndCulturalOfferIdAndArchivedFalse(EXISTING_REVIEW_ID_FOR_CULTURAL_OFFER_ID_1, EXISTING_CULTURAL_OFFER_ID))
                .willReturn(Optional.of(review));

        GetReviewByIdQuery.GetReviewByIdDTO result =
                reviewService.getReview(EXISTING_REVIEW_ID_FOR_CULTURAL_OFFER_ID_1, EXISTING_CULTURAL_OFFER_ID);

        assertNotNull(result);
        assertEquals(EXISTING_REVIEW_ID_FOR_CULTURAL_OFFER_ID_1, result.getId());
        assertEquals(EXISTING_CULTURAL_OFFER_ID, result.getCulturalOfferId());
    }

    @Test(expected = ReviewNotFoundException.class)
    public void givenReviewDoesnExistsThenGetReviewWillFail() throws ReviewNotFoundException {
        given(reviewRepository.findByIdAndCulturalOfferIdAndArchivedFalse(VALID_CULTURAL_OFFER_ID, VALID_REVIEW_ID))
                .willReturn(Optional.empty());

        reviewService.getReview(VALID_CULTURAL_OFFER_ID, VALID_REVIEW_ID);
    }

    /*
    -----------------------
    DELETE
    -----------------------
    */

    @Test
    public void givenValidReviewCommandThenDeleteReviewWillSucceed() throws ReviewNotFoundException {
        Review review = Review.withId(
                VALID_REVIEW_ID,
                validCulturalOffer,
                VALID_REVIEW_COMMENT,
                VALID_REVIEW_RATING,
                false,
                images,
                validRegularUser,
                dateTimeProvider.now());
        given(reviewRepository.findByIdAndCulturalOfferIdAndArchivedFalse(VALID_CULTURAL_OFFER_ID, VALID_REVIEW_ID))
                .willReturn(Optional.of(review));

        reviewService.deleteReviewByCulturalOfferId(VALID_REVIEW_ID, VALID_CULTURAL_OFFER_ID);
    }

    @Test(expected = ReviewNotFoundException.class)
    public void givenReviewDoesntExistThenDeleteReviewWillFail() throws ReviewNotFoundException {
        given(reviewRepository.findByIdAndCulturalOfferIdAndArchivedFalse(VALID_CULTURAL_OFFER_ID, VALID_REVIEW_ID))
                .willReturn(Optional.empty());

        reviewService.deleteReviewByCulturalOfferId(VALID_REVIEW_ID, VALID_CULTURAL_OFFER_ID);
    }
}
