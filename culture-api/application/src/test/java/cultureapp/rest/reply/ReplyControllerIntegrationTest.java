package cultureapp.rest.reply;

import cultureapp.domain.reply.Reply;
import cultureapp.domain.reply.ReplyRepository;
import cultureapp.domain.review.Review;
import cultureapp.domain.review.ReviewRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static cultureapp.common.AdministratorTestData.EXISTING_ADMIN_ID_3;
import static cultureapp.common.AdministratorTestData.NON_EXISTING_ADMIN_ID_10;
import static cultureapp.common.AuthenticationTestData.EXISTING_ADMINISTRATOR_EMAIL;
import static cultureapp.common.AuthenticationTestData.EXISTING_ADMINISTRATOR_PASSWORD;
import static cultureapp.common.AuthenticationTestData.EXISTING_REGULAR_USER_EMAIL;
import static cultureapp.common.AuthenticationTestData.EXISTING_REGULAR_USER_PASSWORD;
import static cultureapp.common.CulturalOfferTestData.NON_EXISTING_CULTURAL_OFFER_ID;
import static cultureapp.common.ReplyTestData.CULTURAL_OFFER_ID_1_WITH_REVIEWS;
import static cultureapp.common.ReplyTestData.INVALID_COMMENT;
import static cultureapp.common.ReplyTestData.REVIEW_ID_3_FOR_OFFER_ID_1_WITH_NO_REPLY;
import static cultureapp.common.ReplyTestData.VALID_COMMENT;
import static cultureapp.common.ReviewTestData.NON_EXISTING_REVIEW_ID_FOR_CULTURAL_OFFER_ID_1;
import static cultureapp.rest.ControllerIntegrationTestUtil.getHeaders;
import static cultureapp.rest.ControllerIntegrationTestUtil.login;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
@Transactional
public class ReplyControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    /*
     * add reply - post
     * offer id          valid
     * review id         valid
     * ReplyRequest      valid
     * admin             invalid -> no token
     * expect            UNAUTHORIZED
     */
    @Test
    public void givenUnauthenticatedAdminRequestThenPostShouldFail() {
        long replyCount = replyRepository.count();

        HttpEntity<ReplyRequest> entity =
                new HttpEntity<>(
                        new ReplyRequest(
                                EXISTING_ADMIN_ID_3,
                                VALID_COMMENT
                        )
                );


        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/cultural-offers/%d/reviews/%d/replies",
                        CULTURAL_OFFER_ID_1_WITH_REVIEWS,
                        REVIEW_ID_3_FOR_OFFER_ID_1_WITH_NO_REPLY),
                entity,
                Void.class
        );

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(replyCount, replyRepository.count());
    }

    /*
     * add reply - post
     * offer id          valid
     * review id         valid
     * ReplyRequest      valid
     * admin             invalid -> not admin
     * expect            FORBIDDEN
     */
    @Test
    public void givenNotAdminRequestThenPostShouldFail() {
        long replyCount = replyRepository.count();

        String token = login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
        HttpHeaders headers = getHeaders(token);
        HttpEntity<ReplyRequest> entity =
                new HttpEntity<>(
                        new ReplyRequest(
                                EXISTING_ADMIN_ID_3,
                                VALID_COMMENT
                        ),
                        headers
                );


        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/cultural-offers/%d/reviews/%d/replies",
                        CULTURAL_OFFER_ID_1_WITH_REVIEWS,
                        REVIEW_ID_3_FOR_OFFER_ID_1_WITH_NO_REPLY),
                entity,
                Void.class
        );

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals(replyCount, replyRepository.count());
    }

    /*
     * add reply - post
     * offer id          invalid -> non existing
     * review id         valid
     * ReplyRequest      valid
     * admin             valid
     * expect            NOT FOUND
     */
    @Test
    public void givenInvalidOfferIdThenPostShouldFail() {
        String token = login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = getHeaders(token);
        HttpEntity<ReplyRequest> entity =
                new HttpEntity<>(
                        new ReplyRequest(
                                EXISTING_ADMIN_ID_3,
                                VALID_COMMENT
                        ),
                        headers
                );

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/cultural-offers/%d/reviews/%d/replies",
                        NON_EXISTING_CULTURAL_OFFER_ID,
                        REVIEW_ID_3_FOR_OFFER_ID_1_WITH_NO_REPLY),
                entity,
                Void.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /*
     * add reply - post
     * offer id          invalid -> null id
     * review id         valid
     * ReplyRequest      valid
     * admin             valid
     * expect            BAD_REQUEST
     */
    @Test
    public void givenNullOfferIdThenPostShouldFail() {
        String token = login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = getHeaders(token);
        HttpEntity<ReplyRequest> entity =
                new HttpEntity<>(
                        new ReplyRequest(
                                EXISTING_ADMIN_ID_3,
                                VALID_COMMENT
                        ),
                        headers
                );

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/cultural-offers/%s/reviews/%d/replies",
                        null,
                        REVIEW_ID_3_FOR_OFFER_ID_1_WITH_NO_REPLY),
                entity,
                Void.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /*
     * add reply - post
     * offer id          valid
     * review id         invalid -> not in offer
     * ReplyRequest      valid
     * admin             valid
     * expect            NOT FOUND
     */
    @Test
    public void givenInvalidReviewIdThenPostShouldFail() {
        String token = login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = getHeaders(token);
        HttpEntity<ReplyRequest> entity =
                new HttpEntity<>(
                        new ReplyRequest(
                                EXISTING_ADMIN_ID_3,
                                VALID_COMMENT
                        ),
                        headers
                );

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/cultural-offers/%d/reviews/%d/replies",
                        CULTURAL_OFFER_ID_1_WITH_REVIEWS,
                        NON_EXISTING_REVIEW_ID_FOR_CULTURAL_OFFER_ID_1),
                entity,
                Void.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /*
     * add reply - post
     * offer id          valid
     * review id         invalid -> null id
     * ReplyRequest      valid
     * admin             valid
     * expect            BAD_REQUEST
     */
    @Test
    public void givenNullReviewIdThenPostShouldFail() {
        String token = login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = getHeaders(token);
        HttpEntity<ReplyRequest> entity =
                new HttpEntity<>(
                        new ReplyRequest(
                                EXISTING_ADMIN_ID_3,
                                VALID_COMMENT
                        ),
                        headers
                );

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/cultural-offers/%d/reviews/%s/replies",
                        CULTURAL_OFFER_ID_1_WITH_REVIEWS,
                        null),
                entity,
                Void.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /*
     * add reply - post
     * offer id          valid
     * review id         valid
     * ReplyRequest      invalid -> non existing admin id
     * admin             valid
     * expect            NOT FOUND
     */
    @Test
    public void givenInvalidAdminIdThenPostShouldFail() {
        String token = login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = getHeaders(token);
        HttpEntity<ReplyRequest> entity =
                new HttpEntity<>(
                        new ReplyRequest(
                                NON_EXISTING_ADMIN_ID_10,
                                VALID_COMMENT
                        ),
                        headers
                );

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/cultural-offers/%d/reviews/%d/replies",
                        CULTURAL_OFFER_ID_1_WITH_REVIEWS,
                        REVIEW_ID_3_FOR_OFFER_ID_1_WITH_NO_REPLY),
                entity,
                Void.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /*
     * add reply - post
     * offer id          valid
     * review id         valid
     * ReplyRequest      invalid -> null admin id
     * admin             valid
     * expect            BAD_REQUEST
     */
    @Test
    public void givenNullAdminIdThenPostShouldFail() {
        String token = login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = getHeaders(token);
        HttpEntity<ReplyRequest> entity =
                new HttpEntity<>(
                        new ReplyRequest(
                                null,
                                VALID_COMMENT
                        ),
                        headers
                );

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/cultural-offers/%d/reviews/%d/replies",
                        CULTURAL_OFFER_ID_1_WITH_REVIEWS,
                        REVIEW_ID_3_FOR_OFFER_ID_1_WITH_NO_REPLY),
                entity,
                Void.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /*
     * add reply - post
     * offer id          valid
     * review id         valid
     * ReplyRequest      invalid -> invalid comment
     * admin             valid
     * expect            BAD_REQUEST
     */
    @Test
    public void givenInvalidCommentThenPostShouldFail() {
        String token = login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = getHeaders(token);
        HttpEntity<ReplyRequest> entity =
                new HttpEntity<>(
                        new ReplyRequest(
                                EXISTING_ADMIN_ID_3,
                                INVALID_COMMENT
                        ),
                        headers
                );

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/cultural-offers/%d/reviews/%d/replies",
                        CULTURAL_OFFER_ID_1_WITH_REVIEWS,
                        REVIEW_ID_3_FOR_OFFER_ID_1_WITH_NO_REPLY),
                entity,
                Void.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /*
     * add reply - post
     * offer id          valid
     * review id         valid
     * ReplyRequest      invalid -> null comment
     * admin             valid
     * expect            BAD_REQUEST
     */
    @Test
    public void givenNullCommentThenPostShouldFail() {
        String token = login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = getHeaders(token);
        HttpEntity<ReplyRequest> entity =
                new HttpEntity<>(
                        new ReplyRequest(
                                EXISTING_ADMIN_ID_3,
                                null
                        ),
                        headers
                );

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/cultural-offers/%d/reviews/%d/replies",
                        CULTURAL_OFFER_ID_1_WITH_REVIEWS,
                        REVIEW_ID_3_FOR_OFFER_ID_1_WITH_NO_REPLY),
                entity,
                Void.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
