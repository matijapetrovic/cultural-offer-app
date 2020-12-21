package cultureapp.domain.review;

import cultureapp.common.ReviewTestData;
import cultureapp.domain.core.AuthenticationService;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.image.exception.ImageNotFoundException;
import cultureapp.domain.review.command.AddReviewUseCase;
import cultureapp.domain.review.exception.ReviewNotFoundException;
import cultureapp.domain.review.query.GetReviewByIdQuery;
import cultureapp.domain.review.query.GetReviewsForOfferQuery;
import cultureapp.domain.user.exception.RegularUserNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import java.util.List;
import java.util.Optional;

import static cultureapp.common.AuthenticationTestData.*;
import static cultureapp.common.CulturalOfferTestData.*;
import static cultureapp.common.CulturalOfferTestData.VALID_CULTURAL_OFFER_ID;
import static cultureapp.common.ReviewTestData.*;
import static cultureapp.common.ReviewTestData.VALID_REVIEW_ID;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class ReviewServiceIntegrationTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private AuthenticationService authenticationService;

    /*
    -----------------------
    POST
    -----------------------
    */

    @Test
    public void givenAddReviewCommandIsValidThenAddReviewWillSucceed() throws CulturalOfferNotFoundException, RegularUserNotFoundException, ImageNotFoundException {
        authenticationService.authenticate(EXISTING_REGULAR_USER_EMAIL,EXISTING_REGULAR_USER_PASSWORD);
        long reviewCount = reviewRepository.count();

        AddReviewUseCase.AddReviewCommand command = new AddReviewUseCase.AddReviewCommand(
                EXISTING_CULTURAL_OFFER_ID,
                VALID_REVIEW_COMMENT,
                EXISTING_REVIEW_RATING_FOR_REVIEW_WITH_ID_1,
                List.of());
        reviewService.addReview(command);

       assertEquals(reviewCount + 1, reviewRepository.count());

       Review review = reviewRepository.findAll().get((int) reviewCount);
       assertEquals(VALID_REVIEW_COMMENT, review.getComment());
       assertEquals(EXISTING_REVIEW_RATING_FOR_REVIEW_WITH_ID_1, review.getRating());
       assertEquals(EXISTING_CULTURAL_OFFER_ID, review.getCulturalOffer().getId());
       reviewRepository.delete(review);
    }

    @Test(expected = CulturalOfferNotFoundException.class)
    public void givenCulturalOfferDoesntExistThenAddReviewWillFail() throws CulturalOfferNotFoundException, RegularUserNotFoundException, ImageNotFoundException {
        authenticationService.authenticate(EXISTING_REGULAR_USER_EMAIL,EXISTING_REGULAR_USER_PASSWORD);
        AddReviewUseCase.AddReviewCommand command = new AddReviewUseCase.AddReviewCommand(
                NON_EXISTING_CULTURAL_OFFER_ID,
                VALID_REVIEW_COMMENT,
                VALID_REVIEW_RATING,
                List.of());

        reviewService.addReview(command);
    }

    @Test(expected = ConstraintViolationException.class)
    public void givenCulturalOfferIdIsInvalidThenCreateReviewCommandWillFail() {
        authenticationService.authenticate(EXISTING_REGULAR_USER_EMAIL,EXISTING_REGULAR_USER_PASSWORD);
        new AddReviewUseCase.AddReviewCommand(
                INVALID_CULTURAL_OFFER_ID,
                VALID_REVIEW_COMMENT,
                VALID_REVIEW_RATING,
                List.of());
    }

    /*
    -----------------------
    GET
    -----------------------
    */

    @Test
    public void givenCulturalOfferIdIsValidAndPageIsFirstThenGetReviewsWillReturnNonEmpty() throws CulturalOfferNotFoundException {
        Slice<GetReviewsForOfferQuery.GetReviewsForOfferQueryDTO> result =
                reviewService.getReviewsForOffer(EXISTING_CULTURAL_OFFER_ID, FIRST_PAGE_FOR_CULTURAL_OFFER_ID_1, REVIEW_PAGE_SIZE);

        assertEquals(FIRST_PAGE_NUM_REVIEWS_FOR_CULTURAL_OFFER_ID_1, result.getNumberOfElements());
        assertTrue(result.hasNext());
        assertFalse(result.hasPrevious());
    }

    @Test
    public void givenCulturalOfferIdIsValidAndPageIsLastThenGetReviewsWillReturnNonEmpty() throws CulturalOfferNotFoundException {
        Slice<GetReviewsForOfferQuery.GetReviewsForOfferQueryDTO> result =
                reviewService.getReviewsForOffer(EXISTING_CULTURAL_OFFER_ID, LAST_PAGE_FOR_CULTURAL_OFFER_ID_1, REVIEW_PAGE_SIZE);

        assertEquals(LAST_PAGE_NUM_REVIEWS_FOR_CULTURAL_OFFER_ID_1, result.getNumberOfElements());
        assertFalse(result.hasNext());
        assertTrue(result.hasPrevious());
    }

    @Test(expected = CulturalOfferNotFoundException.class)
    public void givenCulturalOfferDoesntExistThenGetReviewsWillFail() throws CulturalOfferNotFoundException {
        reviewService.getReviewsForOffer(NON_EXISTING_CULTURAL_OFFER_ID, FIRST_PAGE_FOR_CULTURAL_OFFER_ID_1, REVIEW_PAGE_SIZE);
    }

    /*
    -----------------------
    GET SINGLE
    -----------------------
    */

    @Test
    public void getCulturalOfferExistsThenReviewWillSucceed() throws ReviewNotFoundException {
        GetReviewByIdQuery.GetReviewByIdDTO result =
                reviewService.getReview(EXISTING_REVIEW_ID_FOR_CULTURAL_OFFER_ID_1, EXISTING_CULTURAL_OFFER_ID);

        assertNotNull(result);
        assertEquals(EXISTING_REVIEW_ID_FOR_CULTURAL_OFFER_ID_1, result.getId());
        assertEquals(EXISTING_CULTURAL_OFFER_ID, result.getCulturalOfferId());
    }

    @Test(expected = ReviewNotFoundException.class)
    public void givenReviewDoesnExistsThenGetReviewWillFail() throws ReviewNotFoundException {
        reviewService.getReview(VALID_CULTURAL_OFFER_ID, VALID_REVIEW_ID);
    }

    /*
    -----------------------
    DELETE
    -----------------------
    */

    @Test
    public void givenValidReviewCommandThenDeleteReviewWillSucceed() throws ReviewNotFoundException {
        reviewService.deleteReviewByCulturalOfferId(EXISTING_REVIEW_ID_FOR_CULTURAL_OFFER_ID_1, EXISTING_CULTURAL_OFFER_ID);

        Review review = reviewRepository.findById(
                ReviewId.of(EXISTING_REVIEW_ID_FOR_CULTURAL_OFFER_ID_1, EXISTING_CULTURAL_OFFER_ID)).orElseThrow();
        review.unarchive();
        reviewRepository.save(review);
    }

    @Test(expected = ReviewNotFoundException.class)
    public void givenReviewDoesntExistThenDeleteReviewWillFail() throws ReviewNotFoundException {
        reviewService.deleteReviewByCulturalOfferId(VALID_REVIEW_ID, EXISTING_CULTURAL_OFFER_ID);
    }
}
