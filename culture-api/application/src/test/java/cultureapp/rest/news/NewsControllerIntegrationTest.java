package cultureapp.rest.news;

import cultureapp.domain.news.News;
import cultureapp.domain.news.NewsId;
import cultureapp.domain.news.NewsRepository;
import cultureapp.domain.news.query.GetNewsByIdQueryHandler;
import cultureapp.domain.news.query.GetNewsForOfferQueryHandler;
import cultureapp.rest.core.PaginatedResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static cultureapp.common.AdministratorTestData.EXISTING_ADMIN_ID_3;
import static cultureapp.common.AdministratorTestData.NON_EXISTING_ADMIN_ID_10;
import static cultureapp.common.AuthenticationTestData.EXISTING_ADMINISTRATOR_EMAIL;
import static cultureapp.common.AuthenticationTestData.EXISTING_ADMINISTRATOR_PASSWORD;
import static cultureapp.common.AuthenticationTestData.EXISTING_REGULAR_USER_EMAIL;
import static cultureapp.common.AuthenticationTestData.EXISTING_REGULAR_USER_PASSWORD;
import static cultureapp.common.CulturalOfferTestData.EXISTING_CULTURAL_OFFER_ID;
import static cultureapp.common.CulturalOfferTestData.NON_EXISTING_CULTURAL_OFFER_ID;
import static cultureapp.common.NewsTestData.*;
import static cultureapp.rest.ControllerIntegrationTestUtil.getHeaders;
import static cultureapp.rest.ControllerIntegrationTestUtil.login;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
//@RequestMapping(value="/api/cultural-offers/{culturalOfferId}/news")
public class NewsControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private NewsRepository newsRepository;

    /*
     * create news - post
     * offer id              valid
     * NewsRequest           valid
     * user                  valid
     * expect                OK
     */
    @Test
    public void givenAdminIsLoggedInAndRequestIsValidThenPostShouldSucceed() {
        long newsCount = newsRepository.count();

        String token = login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = getHeaders(token);
        HttpEntity<NewsRequest> entity =
                new HttpEntity<>(
                        new NewsRequest(
                                NON_EXISTING_NEWS_TITLE,
//                                EXISTING_ADMIN_ID_3,
                                NON_EXISTING_NEWS_TEXT,
                                List.of(FREE_NEWS_IMAGE_ID_10)
                        ),
                        headers
                );

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/cultural-offers/%d/news", EXISTING_CULTURAL_OFFER_ID),
                entity,
                Void.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(newsCount + 1, newsRepository.count());

        News news = newsRepository.findAll().get((int) newsCount);
        assertEquals(EXISTING_CULTURAL_OFFER_ID, news.getCulturalOffer().getId());
        assertEquals(NON_EXISTING_NEWS_TITLE, news.getTitle());

        // Rollback
        newsRepository.delete(news);
    }

    /*
     * create news - post
     * offer id              invalid -> non existing
     * NewsRequest           valid
     * user                  valid
     * expect                NOT FOUND
     */
    @Test
    public void givenInvalidOfferIdThenPostShouldFail() {
        String token = login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = getHeaders(token);
        HttpEntity<NewsRequest> entity =
                new HttpEntity<>(
                        new NewsRequest(
                                NON_EXISTING_NEWS_TITLE,
//                                EXISTING_ADMIN_ID_3,
                                NON_EXISTING_NEWS_TEXT,
                                List.of(FREE_NEWS_IMAGE_ID_9)
                        ),
                        headers
                );

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/cultural-offers/%d/news", NON_EXISTING_CULTURAL_OFFER_ID),
                entity,
                Void.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /*
     * create news - post
     * offer id              valid
     * NewsRequest           invalid -> title is empty
     * user                  valid
     * expect                BAD REQUEST
     */
    @Test
    public void givenEmptyTitleRequestThenPostShouldFail() {
        String token = login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = getHeaders(token);
        HttpEntity<NewsRequest> entity =
                new HttpEntity<>(
                        new NewsRequest(
                                INVALID_NEWS_TITLE,
//                                EXISTING_ADMIN_ID_3,
                                NON_EXISTING_NEWS_TEXT,
                                List.of(FREE_NEWS_IMAGE_ID_9)
                        ),
                        headers
                );

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/cultural-offers/%d/news", EXISTING_CULTURAL_OFFER_ID),
                entity,
                Void.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /*
     * create news - post
     * offer id              valid
     * NewsRequest           invalid -> title is null
     * user                  valid
     * expect                BAD REQUEST
     */
    @Test
    public void givenNullTitleRequestThenPostShouldFail() {
        String token = login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = getHeaders(token);
        HttpEntity<NewsRequest> entity =
                new HttpEntity<>(
                        new NewsRequest(
                                null,
//                                EXISTING_ADMIN_ID_3,
                                NON_EXISTING_NEWS_TEXT,
                                List.of(FREE_NEWS_IMAGE_ID_9)
                        ),
                        headers
                );

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/cultural-offers/%d/news", EXISTING_CULTURAL_OFFER_ID),
                entity,
                Void.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /*
     * create news - post
     * offer id              valid
     * NewsRequest           invalid -> non existing author id
     * user                  valid
     * expect                NOT FOUND
     */
    @Test
    public void givenInvalidAuthorIdRequestThenPostShouldFail() {
        String token = login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = getHeaders(token);
        HttpEntity<NewsRequest> entity =
                new HttpEntity<>(
                        new NewsRequest(
                                NON_EXISTING_NEWS_TEXT,
//                                NON_EXISTING_ADMIN_ID_10,
                                NON_EXISTING_NEWS_TEXT,
                                List.of(FREE_NEWS_IMAGE_ID_9)
                        ),
                        headers
                );

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/cultural-offers/%d/news", EXISTING_CULTURAL_OFFER_ID),
                entity,
                Void.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /*
     * create news - post
     * offer id              valid
     * NewsRequest           invalid -> null author id
     * user                  valid
     * expect                BAD REQUEST
     */
    @Test
    public void givenNullAuthorIdRequestThenPostShouldFail() {
        String token = login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = getHeaders(token);
        HttpEntity<NewsRequest> entity =
                new HttpEntity<>(
                        new NewsRequest(
                                NON_EXISTING_NEWS_TEXT,
//                                null,
                                NON_EXISTING_NEWS_TEXT,
                                List.of(FREE_NEWS_IMAGE_ID_9)
                        ),
                        headers
                );

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/cultural-offers/%d/news", EXISTING_CULTURAL_OFFER_ID),
                entity,
                Void.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /*
     * create news - post
     * offer id              valid
     * NewsRequest           invalid -> empty text
     * user                  valid
     * expect                BAD REQUEST
     */
    @Test
    public void givenEmptyTextRequestThenPostShouldFail() {
        String token = login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = getHeaders(token);
        HttpEntity<NewsRequest> entity =
                new HttpEntity<>(
                        new NewsRequest(
                                NON_EXISTING_NEWS_TEXT,
//                                EXISTING_ADMIN_ID_3,
                                INVALID_NEWS_TEXT,
                                List.of(FREE_NEWS_IMAGE_ID_9)
                        ),
                        headers
                );

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/cultural-offers/%d/news", EXISTING_CULTURAL_OFFER_ID),
                entity,
                Void.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /*
     * create news - post
     * offer id              valid
     * NewsRequest           invalid -> null text
     * user                  valid
     * expect                BAD REQUEST
     */
    @Test
    public void givenNullTextRequestThenPostShouldFail() {
        String token = login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = getHeaders(token);
        HttpEntity<NewsRequest> entity =
                new HttpEntity<>(
                        new NewsRequest(
                                NON_EXISTING_NEWS_TEXT,
//                                EXISTING_ADMIN_ID_3,
                                null,
                                List.of(FREE_NEWS_IMAGE_ID_9)
                        ),
                        headers
                );

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/cultural-offers/%d/news", EXISTING_CULTURAL_OFFER_ID),
                entity,
                Void.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /*
     * create news - post
     * offer id              valid
     * NewsRequest           invalid -> null image
     * user                  valid
     * expect                BAD REQUEST
     */
    @Test
    public void givenNullImageRequestThenPostShouldFail() {
        String token = login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = getHeaders(token);
        HttpEntity<NewsRequest> entity =
                new HttpEntity<>(
                        new NewsRequest(
                                NON_EXISTING_NEWS_TEXT,
//                                EXISTING_ADMIN_ID_3,
                                NON_EXISTING_NEWS_TEXT,
                                null
                        ),
                        headers
                );

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/cultural-offers/%d/news", EXISTING_CULTURAL_OFFER_ID),
                entity,
                Void.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /*
     * create news - post
     * offer id              valid
     * NewsRequest           valid
     * user                  invalid -> no token
     * expect                UNAUTHORIZED
     */
    @Test
    public void givenUnauthenticatedUserRequestThenPostShouldFail() {
        long newsCount = newsRepository.count();

        HttpEntity<NewsRequest> entity =
                new HttpEntity<>(
                        new NewsRequest(
                                NON_EXISTING_NEWS_TEXT,
//                                EXISTING_ADMIN_ID_3,
                                NON_EXISTING_NEWS_TEXT,
                                List.of(FREE_NEWS_IMAGE_ID_9)
                        )
                );

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/cultural-offers/%d/news", EXISTING_CULTURAL_OFFER_ID),
                entity,
                Void.class
        );

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(newsCount, newsRepository.count());

    }

    /*
     * create news - post
     * offer id              valid
     * NewsRequest           valid
     * user                  invalid -> not admin
     * expect                FORBIDDEN
     */
    @Test
    public void givenNotAdminRequestThenPostShouldFail() {
        long newsCount = newsRepository.count();

        String token = login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
        HttpHeaders headers = getHeaders(token);
        HttpEntity<NewsRequest> entity =
                new HttpEntity<>(
                        new NewsRequest(
                                NON_EXISTING_NEWS_TEXT,
//                                EXISTING_ADMIN_ID_3,
                                NON_EXISTING_NEWS_TEXT,
                                List.of(FREE_NEWS_IMAGE_ID_9)
                        ),
                        headers
                );

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/cultural-offers/%d/news", EXISTING_CULTURAL_OFFER_ID),
                entity,
                Void.class
        );

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals(newsCount, newsRepository.count());

    }

    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////

    private int FIRST_PAGE = 0;
    private int SECOND_PAGE = 1;
    private int SIZE = 5;

    /*
    * get all news for offer
    * offer id          valid
    * page              valid
    * size              valid
    * expect            OK
    */
    @Test
    public void givenValidOfferIdAndFirstPageThenNewsGetShouldReturnNonEmpty() {
        ResponseEntity<PaginatedResponse<GetNewsForOfferQueryHandler.GetNewsForOfferDTO>> response =
                restTemplate.exchange(
                        String.format("/api/cultural-offers/%d/news?page=%d&limit=%d",
                                EXISTING_CULTURAL_OFFER_ID,
                                FIRST_PAGE,
                                SIZE
                                ),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {}
                );

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());

        assertEquals(NUM_OF_NON_ARCHIVED_NEWS_PER_OFFER, response.getBody().getData().size());
        assertTrue(response.getBody().getLinks().containsKey("self"));
        assertFalse(response.getBody().getLinks().containsKey("next"));
        assertFalse(response.getBody().getLinks().containsKey("prev"));
    }

    /*
     * get all news for offer
     * offer id          invalid -> non existing id
     * page              valid
     * size              valid
     * expect            NOT FOUND
     */
    @Test
    public void givenInvalidOfferIdThenNewsGetShouldFail() {
        ResponseEntity<PaginatedResponse<GetNewsForOfferQueryHandler.GetNewsForOfferDTO>> response =
                restTemplate.exchange(
                        String.format("/api/cultural-offers/%d/news?page=%d&limit=%d",
                                NON_EXISTING_CULTURAL_OFFER_ID,
                                FIRST_PAGE,
                                SIZE
                        ),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {}
                );

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /*
     * get all news for offer
     * offer id          invalid -> null id
     * page              valid
     * size              valid
     * expect            BAD REQUEST
     */
    @Test
    public void givenNullOfferIdThenNewsGetShouldFail() {
        ResponseEntity<PaginatedResponse<GetNewsForOfferQueryHandler.GetNewsForOfferDTO>> response =
                restTemplate.exchange(
                        String.format("/api/cultural-offers/%s/news?page=%d&limit=%d",
                                null,
                                FIRST_PAGE,
                                SIZE
                        ),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {}
                );

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /*
     * get all news for offer
     * offer id          valid
     * page              invalid -> negative
     * size              valid
     * expect            BAD REQUEST
     */
    @Test
    public void givenInvalidPageThenNewsGetShouldFail() {
        ResponseEntity<PaginatedResponse<GetNewsForOfferQueryHandler.GetNewsForOfferDTO>> response =
                restTemplate.exchange(
                        String.format("/api/cultural-offers/%d/news?page=%d&limit=%d",
                                EXISTING_CULTURAL_OFFER_ID,
                                -1,
                                SIZE
                        ),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {}
                );

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /*
     * get all news for offer
     * offer id          valid
     * page              invalid -> null
     * size              valid
     * expect            BAD REQUEST
     */
    @Test
    public void givenNullPageThenNewsGetShouldFail() {
        ResponseEntity<PaginatedResponse<GetNewsForOfferQueryHandler.GetNewsForOfferDTO>> response =
                restTemplate.exchange(
                        String.format("/api/cultural-offers/%d/news?page=%s&limit=%d",
                                EXISTING_CULTURAL_OFFER_ID,
                                null,
                                SIZE
                        ),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {}
                );

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /*
     * get all news for offer
     * offer id          valid
     * page              valid
     * size              invalid -> negative
     * expect            BAD REQUEST
     */
    @Test
    public void givenInvalidSizeThenNewsGetShouldFail() {
        ResponseEntity<PaginatedResponse<GetNewsForOfferQueryHandler.GetNewsForOfferDTO>> response =
                restTemplate.exchange(
                        String.format("/api/cultural-offers/%d/news?page=%d&limit=%d",
                                EXISTING_CULTURAL_OFFER_ID,
                                FIRST_PAGE,
                                -1
                        ),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {}
                );

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /*
     * get all news for offer
     * offer id          valid
     * page              valid
     * size              invalid -> null
     * expect            BAD REQUEST
     */
    @Test
    public void givenNullSizeThenNewsGetShouldFail() {
        ResponseEntity<PaginatedResponse<GetNewsForOfferQueryHandler.GetNewsForOfferDTO>> response =
                restTemplate.exchange(
                        String.format("/api/cultural-offers/%d/news?page=%d&limit=%d",
                                EXISTING_CULTURAL_OFFER_ID,
                                FIRST_PAGE,
                                null
                        ),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {}
                );

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /*
     * get all news for offer
     * offer id          valid
     * page              invalid -> page with no content
     * size              valid
     * expect            OK
     */
    @Test
    public void givenEmptyPageThenNewsGetShouldFail() {
        ResponseEntity<PaginatedResponse<GetNewsForOfferQueryHandler.GetNewsForOfferDTO>> response =
                restTemplate.exchange(
                        String.format("/api/cultural-offers/%d/news?page=%d&limit=%d",
                                EXISTING_CULTURAL_OFFER_ID,
                                SECOND_PAGE,
                                SIZE
                        ),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {}
                );

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());

        assertEquals(0, response.getBody().getData().size());
        assertTrue(response.getBody().getLinks().containsKey("self"));
        assertFalse(response.getBody().getLinks().containsKey("next"));
        assertTrue(response.getBody().getLinks().containsKey("prev"));
    }

    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////

    /*
    * get news by id
    * offer id          valid
    * news id           valid
    * expected          OK
    */
    @Test
    public void givenOfferIdAndNewsIdAreValidThenNewsGetShouldSucceed(){
        ResponseEntity<GetNewsByIdQueryHandler.GetNewsByIdDTO> response =
                restTemplate.exchange(
                        String.format(
                                "/api/cultural-offers/%d/news/%d",
                                EXISTING_CULTURAL_OFFER_ID,
                                EXISTING_NEWS_ID_1_FOR_OFFER_ID_1
                        ),
                        HttpMethod.GET,
                        null,
                        GetNewsByIdQueryHandler.GetNewsByIdDTO.class
                );

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(response.getBody());
        assertEquals(EXISTING_CULTURAL_OFFER_ID, response.getBody().getCulturalOfferId());
        assertEquals(EXISTING_NEWS_ID_1_FOR_OFFER_ID_1, response.getBody().getId());
        assertEquals(EXISTING_NEWS_TITLE_1_FOR_OFFER_ID_1, response.getBody().getTitle());
    }

    /*
     * get news by id
     * offer id          invalid -> non existing id
     * news id           valid
     * expected          NOT FOUND
     */
    @Test
    public void givenInvalidOfferIdThenNewsGetByIdShouldFail(){
        ResponseEntity<GetNewsByIdQueryHandler.GetNewsByIdDTO> response =
                restTemplate.exchange(
                        String.format(
                                "/api/cultural-offers/%d/news/%d",
                                NON_EXISTING_CULTURAL_OFFER_ID,
                                EXISTING_NEWS_ID_1_FOR_OFFER_ID_1
                        ),
                        HttpMethod.GET,
                        null,
                        GetNewsByIdQueryHandler.GetNewsByIdDTO.class
                );

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /*
     * get news by id
     * offer id          invalid -> null id
     * news id           valid
     * expected          BAD_REQUEST
     */
    @Test
    public void givenNullOfferIdThenNewsGetByIdShouldFail(){
        ResponseEntity<GetNewsByIdQueryHandler.GetNewsByIdDTO> response =
                restTemplate.exchange(
                        String.format(
                                "/api/cultural-offers/%s/news/%d",
                                null,
                                EXISTING_NEWS_ID_1_FOR_OFFER_ID_1
                        ),
                        HttpMethod.GET,
                        null,
                        GetNewsByIdQueryHandler.GetNewsByIdDTO.class
                );

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /*
     * get news by id
     * offer id          valid
     * news id           invalid -> not in offer
     * expected          BAD_REQUEST
     */
    @Test
    public void givenInvalidNewsIdThenNewsGetByIdShouldFail(){
        ResponseEntity<GetNewsByIdQueryHandler.GetNewsByIdDTO> response =
                restTemplate.exchange(
                        String.format(
                                "/api/cultural-offers/%d/news/%d",
                                EXISTING_CULTURAL_OFFER_ID,
                                NON_EXISTING_NEWS_ID_FOR_OFFER_ID_1
                        ),
                        HttpMethod.GET,
                        null,
                        GetNewsByIdQueryHandler.GetNewsByIdDTO.class
                );

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /*
     * get news by id
     * offer id          valid
     * news id           invalid -> null id
     * expected          BAD_REQUEST
     */
    @Test
    public void givenNullNewsIdThenNewsGetByIdShouldFail(){
        ResponseEntity<GetNewsByIdQueryHandler.GetNewsByIdDTO> response =
                restTemplate.exchange(
                        String.format(
                                "/api/cultural-offers/%d/news/",
                                EXISTING_CULTURAL_OFFER_ID
                        ),
                        HttpMethod.GET,
                        null,
                        GetNewsByIdQueryHandler.GetNewsByIdDTO.class
                );

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////

    /*
    * update news
    * offer id          valid
    * news id           valid
    * NewsRequest       valid
    * user              valid
    * expected          OK
    */
    @Test
    public void givenAllValidInputThenNewsUpdateShouldSucceed(){
        String token = login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
//        String token = login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
        HttpHeaders headers = getHeaders(token);
        HttpEntity<NewsRequest> entity =
                new HttpEntity<>(
                        new NewsRequest(
                                NON_EXISTING_NEWS_TITLE,
//                                EXISTING_ADMIN_ID_3,
                                NON_EXISTING_NEWS_TEXT,
                                List.of(FREE_NEWS_IMAGE_ID_9)
                        ),
                        headers
                );

        ResponseEntity<Void> response = restTemplate.exchange(
                String.format(
                        "/api/cultural-offers/%d/news/%d",
                        EXISTING_CULTURAL_OFFER_ID,
                        EXISTING_NEWS_ID_1_FOR_OFFER_ID_1
                ),
                HttpMethod.PUT,
                entity,
                Void.class
        );

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Optional<News> newsOptional =
                newsRepository.findByIdAndCulturalOfferIdAndArchivedFalse(
                        EXISTING_NEWS_ID_1_FOR_OFFER_ID_1,
                        EXISTING_CULTURAL_OFFER_ID
                );
        assertTrue(newsOptional.isPresent());

        News news = newsOptional.get();
        assertEquals(NON_EXISTING_NEWS_TITLE, news.getTitle());
        assertEquals(NON_EXISTING_NEWS_TEXT, news.getText());
        assertEquals(EXISTING_ADMIN_ID_3, news.getAuthor().getId());

        // Rollback
        news.setTitle(EXISTING_NEWS_TITLE_1_FOR_OFFER_ID_1);
        news.setText(EXISTING_NEWS_TEXT);
        newsRepository.save(news);
    }

    /*
     * update news
     * offer id          valid
     * news id           valid
     * NewsRequest       valid
     * user              invalid -> unauthenticated
     * expected          UNAUTHORIZED
     */
    @Test
    public void givenUnauthenticatedUserThenNewsUpdateShouldFail(){
//        String token = login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
//        String token = login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
//        HttpHeaders headers = getHeaders(token);
        HttpEntity<NewsRequest> entity =
                new HttpEntity<>(
                        new NewsRequest(
                                NON_EXISTING_NEWS_TITLE,
//                                EXISTING_ADMIN_ID_3,
                                NON_EXISTING_NEWS_TEXT,
                                List.of(FREE_NEWS_IMAGE_ID_9)
                        )
                );

        ResponseEntity<Void> response = restTemplate.exchange(
                String.format(
                        "/api/cultural-offers/%d/news/%d",
                        EXISTING_CULTURAL_OFFER_ID,
                        EXISTING_NEWS_ID_1_FOR_OFFER_ID_1
                ),
                HttpMethod.PUT,
                entity,
                Void.class
        );

        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    /*
     * update news
     * offer id          valid
     * news id           valid
     * NewsRequest       valid
     * user              invalid -> not admin
     * expected          FORBIDDEN
     */
    @Test
    public void givenNotAdminThenNewsUpdateShouldFail(){
//        String token = login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        String token = login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
        HttpHeaders headers = getHeaders(token);
        HttpEntity<NewsRequest> entity =
                new HttpEntity<>(
                        new NewsRequest(
                                NON_EXISTING_NEWS_TITLE,
//                                EXISTING_ADMIN_ID_3,
                                NON_EXISTING_NEWS_TEXT,
                                List.of(FREE_NEWS_IMAGE_ID_9)
                        ),
                        headers
                );

        ResponseEntity<Void> response = restTemplate.exchange(
                String.format(
                        "/api/cultural-offers/%d/news/%d",
                        EXISTING_CULTURAL_OFFER_ID,
                        EXISTING_NEWS_ID_1_FOR_OFFER_ID_1
                ),
                HttpMethod.PUT,
                entity,
                Void.class
        );

        assertNotNull(response);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    /*
     * update news
     * offer id          invalid -> not existing
     * news id           valid
     * NewsRequest       valid
     * expected          NOT FOUND
     */
    @Test
    public void givenInvalidOfferIdThenNewsUpdateShouldFail(){
        String token = login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
//        String token = login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
        HttpHeaders headers = getHeaders(token);
        HttpEntity<NewsRequest> entity =
                new HttpEntity<>(
                        new NewsRequest(
                                NON_EXISTING_NEWS_TITLE,
//                                EXISTING_ADMIN_ID_3,
                                NON_EXISTING_NEWS_TEXT,
                                List.of(FREE_NEWS_IMAGE_ID_9)
                        ),
                        headers
                );

        ResponseEntity<Void> response = restTemplate.exchange(
                String.format(
                        "/api/cultural-offers/%d/news/%d",
                        NON_EXISTING_CULTURAL_OFFER_ID,
                        EXISTING_NEWS_ID_1_FOR_OFFER_ID_1
                ),
                HttpMethod.PUT,
                entity,
                Void.class
        );

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /*
     * update news
     * offer id          invalid -> null
     * news id           valid
     * NewsRequest       valid
     * expected          BAD REQUEST
     */
    @Test
    public void givenNullOfferIdThenNewsUpdateShouldFail(){
        String token = login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
//        String token = login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
        HttpHeaders headers = getHeaders(token);
        HttpEntity<NewsRequest> entity =
                new HttpEntity<>(
                        new NewsRequest(
                                NON_EXISTING_NEWS_TITLE,
//                                EXISTING_ADMIN_ID_3,
                                NON_EXISTING_NEWS_TEXT,
                                List.of(FREE_NEWS_IMAGE_ID_9)
                        ),
                        headers
                );

        ResponseEntity<Void> response = restTemplate.exchange(
                String.format(
                        "/api/cultural-offers/%s/news/%d",
                        null,
                        EXISTING_NEWS_ID_1_FOR_OFFER_ID_1
                ),
                HttpMethod.PUT,
                entity,
                Void.class
        );

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /*
     * update news
     * offer id          valid
     * news id           invalid -> not in offer
     * NewsRequest       valid
     * expected          NOT FOUND
     */
    @Test
    public void givenInvalidNewsIdThenNewsUpdateShouldFail(){
        String token = login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
//        String token = login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
        HttpHeaders headers = getHeaders(token);
        HttpEntity<NewsRequest> entity =
                new HttpEntity<>(
                        new NewsRequest(
                                NON_EXISTING_NEWS_TITLE,
//                                EXISTING_ADMIN_ID_3,
                                NON_EXISTING_NEWS_TEXT,
                                List.of(FREE_NEWS_IMAGE_ID_9)
                        ),
                        headers
                );

        ResponseEntity<Void> response = restTemplate.exchange(
                String.format(
                        "/api/cultural-offers/%s/news/%d",
                        EXISTING_CULTURAL_OFFER_ID,
                        NON_EXISTING_NEWS_ID_FOR_OFFER_ID_1
                ),
                HttpMethod.PUT,
                entity,
                Void.class
        );

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /*
     * update news
     * offer id          valid
     * news id           invalid -> null
     * NewsRequest       valid
     * expected          BAD REQUEST
     */
    @Test
    public void givenNullNewsIdThenNewsUpdateShouldFail(){
        String token = login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
//        String token = login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
        HttpHeaders headers = getHeaders(token);
        HttpEntity<NewsRequest> entity =
                new HttpEntity<>(
                        new NewsRequest(
                                NON_EXISTING_NEWS_TITLE,
//                                EXISTING_ADMIN_ID_3,
                                NON_EXISTING_NEWS_TEXT,
                                List.of(FREE_NEWS_IMAGE_ID_9)
                        ),
                        headers
                );

        ResponseEntity<Void> response = restTemplate.exchange(
                String.format(
                        "/api/cultural-offers/%s/news/%s",
                        EXISTING_CULTURAL_OFFER_ID,
                        null
                ),
                HttpMethod.PUT,
                entity,
                Void.class
        );

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /*
     * update news
     * offer id          valid
     * news id           valid
     * NewsRequest       invalid -> invalid title
     * expected          BAD REQUEST
     */
    @Test
    public void givenInvalidTitleThenNewsUpdateShouldFail(){
        String token = login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
//        String token = login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
        HttpHeaders headers = getHeaders(token);
        HttpEntity<NewsRequest> entity =
                new HttpEntity<>(
                        new NewsRequest(
                                INVALID_NEWS_TITLE,
//                                EXISTING_ADMIN_ID_3,
                                NON_EXISTING_NEWS_TEXT,
                                List.of(FREE_NEWS_IMAGE_ID_9)
                        ),
                        headers
                );

        ResponseEntity<Void> response = restTemplate.exchange(
                String.format(
                        "/api/cultural-offers/%d/news/%d",
                        EXISTING_CULTURAL_OFFER_ID,
                        EXISTING_NEWS_ID_1_FOR_OFFER_ID_1
                ),
                HttpMethod.PUT,
                entity,
                Void.class
        );

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /*
     * update news
     * offer id          valid
     * news id           valid
     * NewsRequest       invalid -> null title
     * expected          BAD REQUEST
     */
    @Test
    public void givenNullTitleThenNewsUpdateShouldFail(){
        String token = login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
//        String token = login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
        HttpHeaders headers = getHeaders(token);
        HttpEntity<NewsRequest> entity =
                new HttpEntity<>(
                        new NewsRequest(
                                null,
//                                EXISTING_ADMIN_ID_3,
                                NON_EXISTING_NEWS_TEXT,
                                List.of(FREE_NEWS_IMAGE_ID_9)
                        ),
                        headers
                );

        ResponseEntity<Void> response = restTemplate.exchange(
                String.format(
                        "/api/cultural-offers/%d/news/%d",
                        EXISTING_CULTURAL_OFFER_ID,
                        EXISTING_NEWS_ID_1_FOR_OFFER_ID_1
                ),
                HttpMethod.PUT,
                entity,
                Void.class
        );

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /*
     * update news
     * offer id          valid
     * news id           valid
     * NewsRequest       invalid -> non existing admin id
     * expected          BAD REQUEST
     */
    @Test
    public void givenInvalidAdminIdThenNewsUpdateShouldFail(){
        String token = login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
//        String token = login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
        HttpHeaders headers = getHeaders(token);
        HttpEntity<NewsRequest> entity =
                new HttpEntity<>(
                        new NewsRequest(
                                NON_EXISTING_NEWS_TITLE,
//                                NON_EXISTING_ADMIN_ID_10,
                                NON_EXISTING_NEWS_TEXT,
                                List.of(FREE_NEWS_IMAGE_ID_9)
                        ),
                        headers
                );

        ResponseEntity<Void> response = restTemplate.exchange(
                String.format(
                        "/api/cultural-offers/%d/news/%d",
                        EXISTING_CULTURAL_OFFER_ID,
                        EXISTING_NEWS_ID_1_FOR_OFFER_ID_1
                ),
                HttpMethod.PUT,
                entity,
                Void.class
        );

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /*
     * update news
     * offer id          valid
     * news id           valid
     * NewsRequest       invalid -> null admin id
     * expected          BAD REQUEST
     */
    @Test
    public void givenNullAdminIdThenNewsUpdateShouldFail(){
        String token = login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
//        String token = login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
        HttpHeaders headers = getHeaders(token);
        HttpEntity<NewsRequest> entity =
                new HttpEntity<>(
                        new NewsRequest(
                                NON_EXISTING_NEWS_TITLE,
//                                null,
                                NON_EXISTING_NEWS_TEXT,
                                List.of(FREE_NEWS_IMAGE_ID_9)
                        ),
                        headers
                );

        ResponseEntity<Void> response = restTemplate.exchange(
                String.format(
                        "/api/cultural-offers/%d/news/%d",
                        EXISTING_CULTURAL_OFFER_ID,
                        EXISTING_NEWS_ID_1_FOR_OFFER_ID_1
                ),
                HttpMethod.PUT,
                entity,
                Void.class
        );

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /*
     * update news
     * offer id          valid
     * news id           valid
     * NewsRequest       invalid -> invalid text
     * expected          BAD REQUEST
     */
    @Test
    public void givenInvalidTextThenNewsUpdateShouldFail(){
        String token = login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
//        String token = login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
        HttpHeaders headers = getHeaders(token);
        HttpEntity<NewsRequest> entity =
                new HttpEntity<>(
                        new NewsRequest(
                                NON_EXISTING_NEWS_TITLE,
//                                EXISTING_ADMIN_ID_3,
                                INVALID_NEWS_TEXT,
                                List.of(FREE_NEWS_IMAGE_ID_9)
                        ),
                        headers
                );

        ResponseEntity<Void> response = restTemplate.exchange(
                String.format(
                        "/api/cultural-offers/%d/news/%d",
                        EXISTING_CULTURAL_OFFER_ID,
                        EXISTING_NEWS_ID_1_FOR_OFFER_ID_1
                ),
                HttpMethod.PUT,
                entity,
                Void.class
        );

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /*
     * update news
     * offer id          valid
     * news id           valid
     * NewsRequest       invalid -> null text
     * expected          BAD REQUEST
     */
    @Test
    public void givenNullTextThenNewsUpdateShouldFail(){
        String token = login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
//        String token = login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
        HttpHeaders headers = getHeaders(token);
        HttpEntity<NewsRequest> entity =
                new HttpEntity<>(
                        new NewsRequest(
                                NON_EXISTING_NEWS_TITLE,
//                                EXISTING_ADMIN_ID_3,
                                null,
                                List.of(FREE_NEWS_IMAGE_ID_9)
                        ),
                        headers
                );

        ResponseEntity<Void> response = restTemplate.exchange(
                String.format(
                        "/api/cultural-offers/%d/news/%d",
                        EXISTING_CULTURAL_OFFER_ID,
                        EXISTING_NEWS_ID_1_FOR_OFFER_ID_1
                ),
                HttpMethod.PUT,
                entity,
                Void.class
        );

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    ////

    /*
     * update news
     * offer id          valid
     * news id           valid
     * NewsRequest       invalid -> null images
     * expected          BAD REQUEST
     */
    @Test
    public void givenNullImagesThenNewsUpdateShouldFail(){
        String token = login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
//        String token = login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
        HttpHeaders headers = getHeaders(token);
        HttpEntity<NewsRequest> entity =
                new HttpEntity<>(
                        new NewsRequest(
                                NON_EXISTING_NEWS_TITLE,
//                                EXISTING_ADMIN_ID_3,
                                NON_EXISTING_NEWS_TEXT,
                                null
                        ),
                        headers
                );

        ResponseEntity<Void> response = restTemplate.exchange(
                String.format(
                        "/api/cultural-offers/%d/news/%d",
                        EXISTING_CULTURAL_OFFER_ID,
                        EXISTING_NEWS_ID_1_FOR_OFFER_ID_1
                ),
                HttpMethod.PUT,
                entity,
                Void.class
        );

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////


    /*
    * delete news
    * news id           valid
    * offer id          valid
    * user              valid
    * expect            OK
    */
    @Test
    public void givenNewsIdAndOfferIdAndUserAreValidThenDeleteShouldSucceed() {
        String token = login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
//        String token = login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
        HttpHeaders headers = getHeaders(token);
        HttpEntity<Void> entity =
                new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                String.format("/api/cultural-offers/%d/news/%d", EXISTING_CULTURAL_OFFER_ID, EXISTING_NEWS_ID_1_FOR_OFFER_ID_1),
                HttpMethod.DELETE,
                entity,
                Void.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Optional<News> newsOptional =
                newsRepository.findByIdAndCulturalOfferIdAndArchivedFalse(
                        EXISTING_NEWS_ID_1_FOR_OFFER_ID_1,
                        EXISTING_CULTURAL_OFFER_ID
                );
        assertTrue(newsOptional.isEmpty());

        // Rollback
        News news = newsRepository.findById(
                NewsId.of(EXISTING_NEWS_ID_1_FOR_OFFER_ID_1, EXISTING_CULTURAL_OFFER_ID)
        ).get();

        news.setArchived(false);
        newsRepository.save(news);
    }

    /*
     * delete news
     * news id           valid
     * offer id          valid
     * user              invalid -> unauthenticated
     * expect            UNAUTHORIZED
     */
    @Test
    public void givenUnauthenticatedUserThenDeleteShouldFail() {
//        String token = login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
//        String token = login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
//        HttpHeaders headers = getHeaders(token);
        HttpEntity<NewsRequest> entity =
                new HttpEntity<>(
                        new NewsRequest(
                                NON_EXISTING_NEWS_TITLE,
//                                EXISTING_ADMIN_ID_3,
                                NON_EXISTING_NEWS_TEXT,
                                List.of()
                        )
                );

        ResponseEntity<Void> response = restTemplate.exchange(
                String.format("/api/cultural-offers/%d/news/%d", EXISTING_CULTURAL_OFFER_ID, EXISTING_NEWS_ID_1_FOR_OFFER_ID_1),
                HttpMethod.DELETE,
                entity,
                Void.class
        );

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    /*
     * delete news
     * news id           valid
     * offer id          valid
     * user              invalid -> not admin
     * expect            FORBIDDEN
     */
    @Test
    public void givenNotAdminThenDeleteShouldFail() {
//        String token = login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        String token = login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
        HttpHeaders headers = getHeaders(token);
        HttpEntity<NewsRequest> entity =
                new HttpEntity<>(
                        new NewsRequest(
                                NON_EXISTING_NEWS_TITLE,
//                                EXISTING_ADMIN_ID_3,
                                NON_EXISTING_NEWS_TEXT,
                                List.of()
                        ),
                        headers
                );

        ResponseEntity<Void> response = restTemplate.exchange(
                String.format("/api/cultural-offers/%d/news/%d", EXISTING_CULTURAL_OFFER_ID, EXISTING_NEWS_ID_1_FOR_OFFER_ID_1),
                HttpMethod.DELETE,
                entity,
                Void.class
        );

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    /*
     * delete news
     * news id           invalid -> non existing id
     * offer id          valid
     * user              valid
     * expect            NOT FOUND
     */
    @Test
    public void givenInvalidNewsIdThenDeleteShouldSucceed() {
        String token = login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
//        String token = login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
        HttpHeaders headers = getHeaders(token);
        HttpEntity<Void> entity =
                new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                String.format("/api/cultural-offers/%d/news/%d", EXISTING_CULTURAL_OFFER_ID, NON_EXISTING_NEWS_ID_FOR_OFFER_ID_1),
                HttpMethod.DELETE,
                entity,
                Void.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /*
     * delete news
     * news id           valid
     * offer id          invalid -> non existing id
     * user              valid
     * expect            NOT FOUND
     */
    @Test
    public void givenInvalidOfferIdThenDeleteShouldSucceed() {
        String token = login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
//        String token = login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
        HttpHeaders headers = getHeaders(token);
        HttpEntity<Void> entity =
                new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                String.format("/api/cultural-offers/%d/news/%d", NON_EXISTING_CULTURAL_OFFER_ID, NON_EXISTING_NEWS_ID_FOR_OFFER_ID_1),
                HttpMethod.DELETE,
                entity,
                Void.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
