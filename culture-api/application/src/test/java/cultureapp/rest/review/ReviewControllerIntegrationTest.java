package cultureapp.rest.review;

import static cultureapp.common.AuthenticationTestData.*;
import static cultureapp.common.ReviewTestData.*;
import static cultureapp.common.CulturalOfferTestData.*;

import cultureapp.domain.review.Review;
import cultureapp.domain.review.ReviewId;
import cultureapp.domain.review.ReviewRepository;
import cultureapp.domain.review.query.GetReviewByIdQuery;
import cultureapp.domain.review.query.GetReviewsQuery;
import cultureapp.rest.ControllerIntegrationTestUtil;
import cultureapp.rest.core.PaginatedResponse;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class ReviewControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ReviewRepository reviewRepository;

    /*
    -----------------------
    POST
    -----------------------
    */

    @Test
    public void givenUserIsLoggedInAndRequestIsValidThenReviewPostWillSucceed() {
        long reviewCount = reviewRepository.count();

        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<ReviewRequest> entity =
                new HttpEntity<>(new ReviewRequest(VALID_REVIEW_COMMENT, VALID_REVIEW_RATING, List.of(1L)), headers);

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/cultural-offers/%d/reviews", EXISTING_CULTURAL_OFFER_ID),
                entity,
                Void.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(reviewCount + 1, reviewRepository.count());

        Review review = reviewRepository.findAll().get((int) reviewCount);
        assertEquals(VALID_REVIEW_COMMENT, review.getComment());
        assertEquals(VALID_REVIEW_RATING, review.getRating());
        assertEquals(EXISTING_CULTURAL_OFFER_ID, review.getCulturalOffer().getId());
        reviewRepository.delete(review);
    }

    @Test
    public void givenUnauthenticatedInThenReviewPostWillReturnUnauthorized() {
        long reviewCount = reviewRepository.count();

        HttpEntity<ReviewRequest> entity =
                new HttpEntity<>(new ReviewRequest(VALID_REVIEW_COMMENT, VALID_REVIEW_RATING, List.of()));

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/cultural-offers/%d/reviews", EXISTING_CULTURAL_OFFER_ID),
                entity,
                Void.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(reviewCount, reviewRepository.count());
    }

    @Test
    public void givenNonUserIsLoggedInThenReviewPostWillReturnForbidden() {
        long reviewCount = reviewRepository.count();

        HttpEntity<ReviewRequest> entity =
                new HttpEntity<>(new ReviewRequest(VALID_REVIEW_COMMENT, VALID_REVIEW_RATING, List.of()));

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/cultural-offers/%d/reviews", EXISTING_CULTURAL_OFFER_ID),
                entity,
                Void.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals(reviewCount, reviewRepository.count());
    }

    @Test
    public void givenCulturalOfferDoesntExistThenReviewPostWillReturnNotFound() {
        long reviewCount = reviewRepository.count();

        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<ReviewRequest> entity =
                new HttpEntity<>(new ReviewRequest(VALID_REVIEW_COMMENT, VALID_REVIEW_RATING, List.of()), headers);

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/cultural-offers/%d/reviews", NON_EXISTING_CULTURAL_OFFER_ID),
                entity,
                Void.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals(reviewCount, reviewRepository.count());
    }

    /*
    -----------------------
    GET
    -----------------------
    */

    @Test
    public void givenValidCulturalOfferIdAndFirstPageThenReviewGetWillReturnNonEmpty() {
        ResponseEntity<PaginatedResponse<GetReviewsQuery.GetReviewsQueryDTO>> response =
                restTemplate.exchange(
                        String.format("/api/cultural-offers/%d/reviews?page=%d&limit=%d",
                                EXISTING_CULTURAL_OFFER_ID,
                                FIRST_PAGE_FOR_CULTURAL_OFFER_ID_1,
                                REVIEW_PAGE_SIZE),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());

        assertEquals(FIRST_PAGE_NUM_REVIEWS_FOR_CULTURAL_OFFER_ID_1, response.getBody().getData().size());
        assertTrue(response.getBody().getLinks().containsKey("self"));
        assertTrue(response.getBody().getLinks().containsKey("next"));
        assertFalse(response.getBody().getLinks().containsKey("prev"));
    }

    @Test
    public void givenValidCulturalOfferIdAndLastPageThenReviewsWillReturnNonEmpty() {
        ResponseEntity<PaginatedResponse<GetReviewsQuery.GetReviewsQueryDTO>> response =
                restTemplate.exchange(
                        String.format("/api/cultural-offers/%d/reviews?page=%d&limit=%d",
                                EXISTING_CULTURAL_OFFER_ID,
                                LAST_PAGE_FOR_CULTURAL_OFFER_ID_1,
                                REVIEW_PAGE_SIZE),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());

        assertEquals(LAST_PAGE_FOR_CULTURAL_OFFER_ID_1, response.getBody().getData().size());
        assertTrue(response.getBody().getLinks().containsKey("self"));
        assertFalse(response.getBody().getLinks().containsKey("next"));
        assertTrue(response.getBody().getLinks().containsKey("prev"));
    }

    @Test
    public void givenCulturalOfferDoesntExistThenReviewsGetWillReturnNotFound() {
        ResponseEntity<PaginatedResponse<GetReviewsQuery.GetReviewsQueryDTO>> response =
                restTemplate.exchange(
                        String.format("/api/cultural-offers/%d/reviews?page=%d&limit=%d",
                                NON_EXISTING_CULTURAL_OFFER_ID,
                                LAST_PAGE_FOR_CULTURAL_OFFER_ID_1,
                                REVIEW_PAGE_SIZE),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void givenPageDoesntExistThenReviewsGetWillReturnEmpty() {
        ResponseEntity<PaginatedResponse<GetReviewsQuery.GetReviewsQueryDTO>> response =
                restTemplate.exchange(
                        String.format("/api/cultural-offers/%d/reviews?page=10&limit=%d",
                                NON_EXISTING_CULTURAL_OFFER_ID,
                                REVIEW_PAGE_SIZE),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());
        assertEquals(0, response.getBody().getData().size());
    }

    /*
    -----------------------
    GET SINGLE
    -----------------------
    */

    @Test
    public void givenValidCulturalOfferIdAndValidReviewIdThenReviewGetWillSucceed() {
        ResponseEntity<PaginatedResponse<GetReviewByIdQuery.GetReviewByIdDTO>> response =
                restTemplate.exchange(
                        String.format("/api/cultural-offers/%d/reviews/%d",
                                EXISTING_CULTURAL_OFFER_ID,
                                EXISTING_REVIEW_ID_FOR_CULTURAL_OFFER_ID_1),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(NON_EXISTING_CULTURAL_OFFER_ID, response.getBody().getData());
    }

    @Test
    public void givenCulturalOfferDoesntExistThenReviewGetWillReturnNotFound() {
        ResponseEntity<PaginatedResponse<GetReviewByIdQuery.GetReviewByIdDTO>> response =
                restTemplate.exchange(
                        String.format("/api/cultural-offers/%d/reviews/%d",
                                NON_EXISTING_CULTURAL_OFFER_ID,
                                EXISTING_REVIEW_ID_FOR_CULTURAL_OFFER_ID_1),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

     /*
    -----------------------
    DELETE
    -----------------------
    */

    @Test
    public void givenCulturalOfferExistsThenReviewDeleteWillSucceed() {
        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<ReviewRequest> entity =
                new HttpEntity<>(new ReviewRequest(VALID_REVIEW_COMMENT, VALID_REVIEW_RATING, List.of(1L)), headers);

        ResponseEntity<PaginatedResponse<GetReviewByIdQuery.GetReviewByIdDTO>> response =
                restTemplate.exchange(
                        String.format("/api/cultural-offers/%d/reviews/%d",
                                EXISTING_CULTURAL_OFFER_ID,
                                EXISTING_REVIEW_ID_FOR_CULTURAL_OFFER_ID_1),
                        HttpMethod.DELETE,
                        entity,
                        new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        Optional<Review> reviewOptional =
                reviewRepository.findByIdAndCulturalOfferIdAndArchivedFalse(
                        EXISTING_REVIEW_ID_FOR_CULTURAL_OFFER_ID_1,
                        EXISTING_CULTURAL_OFFER_ID);
        assertTrue(reviewOptional.isEmpty());

        Review review = reviewRepository.findById(
                ReviewId.of(EXISTING_REVIEW_ID_FOR_CULTURAL_OFFER_ID_1, EXISTING_CULTURAL_OFFER_ID)).orElseThrow();
        review.unarchive();
        reviewRepository.save(review);
    }

    @Test
    public void givenUnauthenticatedInThenReviewDeleteWillReturnUnauthorized() {
        HttpEntity<ReviewRequest> entity =
                new HttpEntity<>(new ReviewRequest(VALID_REVIEW_COMMENT, VALID_REVIEW_RATING, List.of(1L)));

        ResponseEntity<PaginatedResponse<GetReviewByIdQuery.GetReviewByIdDTO>> response =
                restTemplate.exchange(
                        String.format("/api/cultural-offers/%d/reviews/%d",
                                EXISTING_CULTURAL_OFFER_ID,
                                EXISTING_REVIEW_ID_FOR_CULTURAL_OFFER_ID_1),
                        HttpMethod.DELETE,
                        entity,
                        new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void givenCulturalOfferDoesntExistThenReviewDeleteWillReturnNotFound() {
        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<ReviewRequest> entity =
                new HttpEntity<>(new ReviewRequest(VALID_REVIEW_COMMENT, VALID_REVIEW_RATING, List.of(1L)), headers);

        ResponseEntity<PaginatedResponse<GetReviewByIdQuery.GetReviewByIdDTO>> response =
                restTemplate.exchange(
                        String.format("/api/cultural-offers/%d/reviews/%d",
                                NON_EXISTING_CULTURAL_OFFER_ID,
                                EXISTING_REVIEW_ID_FOR_CULTURAL_OFFER_ID_1),
                        HttpMethod.DELETE,
                        entity,
                        new ParameterizedTypeReference<>() {});
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void givenReviewDoesntExistThenReviewDeleteWillNotFound() {
        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<ReviewRequest> entity =
                new HttpEntity<>(new ReviewRequest(VALID_REVIEW_COMMENT, VALID_REVIEW_RATING, List.of(1L)), headers);

        ResponseEntity<PaginatedResponse<GetReviewByIdQuery.GetReviewByIdDTO>> response =
                restTemplate.exchange(
                        String.format("/api/cultural-offers/%d/reviews/%d",
                                EXISTING_CULTURAL_OFFER_ID,
                                NON_EXISTING_REVIEW_ID_FOR_CULTURAL_OFFER_ID_1),
                        HttpMethod.DELETE,
                        entity,
                        new ParameterizedTypeReference<>() {});
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
