
package cultureapp.rest.subscription;

import static cultureapp.common.SubcategoryTestData.*;
import static cultureapp.common.CategoryTestData.*;
import static cultureapp.common.AuthenticationTestData.*;
import static cultureapp.common.CulturalOfferTestData.*;


import static org.junit.Assert.*;

import cultureapp.domain.cultural_offer.query.GetSubscriptionsForUserQueryHandler;
import cultureapp.rest.ControllerIntegrationTestUtil;

import cultureapp.rest.core.PaginatedResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class SubscriptionControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void givenValidCategoryIdAndSubcategoryIdAndRegularUserThenGetSubscriptionsWillReturnNonEmpty() {
        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<Void> entity =
                new HttpEntity<>(headers);

        ResponseEntity<PaginatedResponse<GetSubscriptionsForUserQueryHandler.GetSubscriptionsForUserDTO>> response =
                restTemplate.exchange(
                        String.format("/api/subscriptions?categoryId=%d&subcategoryId=%d&page=0&limit=3", EXISTING_CATEGORY_ID, EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1),
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(response.getBody());
        assertEquals(NUMBER_OF_SUBSCRIPTIONS_FOR_USER_1_AND_SUBCATEGORY_1_1, response.getBody().getData().size());
        assertEquals(EXISTING_CULTURAL_OFFER_ID, response.getBody().getData().get(0).getId());
    }

    @Test
    public void givenSubcategoryDoesntExistThenGetSubscriptionsWillReturnNotFound() {
        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<Void> entity =
                new HttpEntity<>(headers);

        ResponseEntity<Object> response =
                restTemplate.exchange(
                        String.format("/api/subscriptions?categoryId=%d&subcategoryId=%d&page=0&limit=3", EXISTING_CATEGORY_ID, NON_EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1),
                        HttpMethod.GET,
                        entity,
                        Object.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void givenSubscriptionsDontExistThenGetSubscriptionsWillReturnEmpty() {
        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<Void> entity =
                new HttpEntity<>(headers);

        ResponseEntity<PaginatedResponse<GetSubscriptionsForUserQueryHandler.GetSubscriptionsForUserDTO>> response =
                restTemplate.exchange(
                        String.format("/api/subscriptions?categoryId=%d&subcategoryId=%d&page=0&limit=3", EXISTING_CATEGORY_ID, 7L),
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(response.getBody());

        assertEquals(0, response.getBody().getData().size());
    }

    @Test
    public void givenMissingQueryParamsThenGetSubscriptionsWillReturnBadRequest() {
        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<Void> entity =
                new HttpEntity<>(headers);

        ResponseEntity<Object> response =
                restTemplate.exchange(
                        "/api/subscriptions",
                        HttpMethod.GET,
                        entity,
                        Object.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    public void givenUnauthenticatedUserThenGetSubscriptionsWillReturnUnauthorized() {
        ResponseEntity<Object> response =
                restTemplate.exchange(
                        "/api/subscriptions",
                        HttpMethod.GET,
                        null,
                        Object.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void givenNonRegularUserIsLoggedInThenGetSubscriptionsWillReturnForbidden() {
        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<Void> entity =
                new HttpEntity<>(headers);

        ResponseEntity<Object> response =
                restTemplate.exchange(
                        String.format("/api/subscriptions?categoryId=%d&subcategoryId=%d&page=0&limit=3", EXISTING_CATEGORY_ID, EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1),
                        HttpMethod.GET,
                        entity,
                        Object.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

}
