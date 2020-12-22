package cultureapp.rest.cultural_offer;

import cultureapp.domain.account.Account;
import cultureapp.domain.account.AccountRepository;
import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.CulturalOfferRepository;
import cultureapp.domain.cultural_offer.query.GetCulturalOfferLocationsQueryHandler;
import cultureapp.domain.user.RegularUser;
import cultureapp.domain.user.RegularUserRepository;
import cultureapp.rest.ControllerIntegrationTestUtil;
import cultureapp.rest.subcategory.SubcategoryRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static cultureapp.common.AuthenticationTestData.*;
import static cultureapp.common.CategoryTestData.EXISTING_CATEGORY_ID;
import static cultureapp.common.CategoryTestData.NON_EXISTING_CATEGORY_ID;
import static cultureapp.common.CulturalOfferTestData.*;
import static cultureapp.common.SubcategoryTestData.EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1;
import static cultureapp.common.SubcategoryTestData.NON_EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CulturalOfferControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CulturalOfferRepository culturalOfferRepository;

    @Autowired
    private RegularUserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    // subscribe()

    @Test
    public void givenRegularUserIsLoggedInAndRequestIsValidThenSubscriptionPostWillSucceed() {
        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<SubcategoryRequest> entity =
                new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/cultural-offers/%d/subscriptions", NON_EXISTING_SUBSCRIPTION_ID_FOR_USER_1),
                entity,
                Void.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        Account account = accountRepository.findByEmail(EXISTING_REGULAR_USER_EMAIL).orElse(null);
        assertNotNull(account);
        RegularUser user = userRepository.findByAccountIdWithSubscriptions(account.getId()).orElse(null);
        assertNotNull(user);

        Set<CulturalOffer> culturalOffers = user.getCulturalOffers();
        assertEquals(NUMBER_OF_SUBSCRIPTIONS_FOR_USER_1 + 1, culturalOffers.size());

        Optional<CulturalOffer> offer = culturalOffers
                .stream()
                .filter(culturalOffer -> culturalOffer.getId().equals(NON_EXISTING_SUBSCRIPTION_ID_FOR_USER_1))
                .findAny();
        assertFalse(offer.isEmpty());
        user.unsubscribe(offer.get());
        userRepository.save(user);
    }

    @Test
    public void givenCulturalOfferDoesNotExistThenSubscriptionsPostWillReturnNotFound() {
        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<SubcategoryRequest> entity =
                new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/cultural-offers/%d/subscriptions", NON_EXISTING_CULTURAL_OFFER_ID),
                entity,
                Void.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        Account account = accountRepository.findByEmail(EXISTING_REGULAR_USER_EMAIL).orElse(null);
        assertNotNull(account);
        RegularUser user = userRepository.findByAccountIdWithSubscriptions(account.getId()).orElse(null);
        assertNotNull(user);

        Set<CulturalOffer> culturalOffers = user.getCulturalOffers();
        assertEquals(NUMBER_OF_SUBSCRIPTIONS_FOR_USER_1, culturalOffers.size());

    }


    @Test
    public void givenUnauthenticatedThenSubscriptionPostWillReturnUnauthorized() {
        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/cultural-offers/%d/subscriptions", NON_EXISTING_SUBSCRIPTION_ID_FOR_USER_1),
                null,
                Void.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }


    @Test
    public void givenNonRegularUserIsLoggedInThenSubscriptionsPostWillReturnForbidden() {
        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<SubcategoryRequest> entity =
                new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/cultural-offers/%d/subscriptions", NON_EXISTING_SUBSCRIPTION_ID_FOR_USER_1),
                entity,
                Void.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void givenInvalidCulturalOfferIdThenSubscriptionsPostWillReturnBadRequest() {
        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<SubcategoryRequest> entity =
                new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/cultural-offers/%d/subscriptions", INVALID_CULTURAL_OFFER_ID),
                entity,
                Void.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void givenSubscriptionAlreadyExistsThenSubscriptionsPostWillReturnConflict() {
        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<SubcategoryRequest> entity =
                new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/cultural-offers/%d/subscriptions", EXISTING_SUBSCRIPTION_ID_FOR_USER_1),
                entity,
                Void.class);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());

        Account account = accountRepository.findByEmail(EXISTING_REGULAR_USER_EMAIL).orElse(null);
        assertNotNull(account);
        RegularUser user = userRepository.findByAccountIdWithSubscriptions(account.getId()).orElse(null);
        assertNotNull(user);

        Set<CulturalOffer> culturalOffers = user.getCulturalOffers();
        assertEquals(NUMBER_OF_SUBSCRIPTIONS_FOR_USER_1, culturalOffers.size());

    }

    // unsubscribe
    @Test
    public void givenRegularUserIsLoggedInAndRequestIsValidThenSubscriptionDeleteWillSucceed() {
        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_REGULAR_USER_EMAIL2, EXISTING_REGULAR_USER_PASSWORD2);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<SubcategoryRequest> entity =
                new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                String.format("/api/cultural-offers/%d/subscriptions", EXISTING_SUBSCRIPTION_ID_FOR_USER_2),
                HttpMethod.DELETE,
                entity,
                Void.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        Account account = accountRepository.findByEmail(EXISTING_REGULAR_USER_EMAIL2).orElse(null);
        assertNotNull(account);
        RegularUser user = userRepository.findByAccountIdWithSubscriptions(account.getId()).orElse(null);
        assertNotNull(user);

        Set<CulturalOffer> culturalOffers = user.getCulturalOffers();
        assertEquals(NUMBER_OF_SUBSCRIPTIONS_FOR_USER_2 - 1, culturalOffers.size());

        boolean deleted = culturalOffers
                .stream()
                .noneMatch(culturalOffer -> culturalOffer.getId().equals(EXISTING_SUBSCRIPTION_ID_FOR_USER_2));
        assertTrue(deleted);

        CulturalOffer offer = culturalOfferRepository.findById(EXISTING_SUBSCRIPTION_ID_FOR_USER_2).orElse(null);
        assertNotNull(offer);
        user.subscribe(offer);
        userRepository.save(user);
    }

    @Test
    public void givenCulturalOfferDoesNotExistThenSubscriptionsDeleteWillReturnNotFound() {
        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_REGULAR_USER_EMAIL2, EXISTING_REGULAR_USER_PASSWORD2);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<SubcategoryRequest> entity =
                new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                String.format("/api/cultural-offers/%d/subscriptions", NON_EXISTING_CULTURAL_OFFER_ID),
                HttpMethod.DELETE,
                entity,
                Void.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    public void givenUnauthenticatedThenSubscriptionDeleteWillReturnUnauthorized() {
        ResponseEntity<Void> response = restTemplate.exchange(
                String.format("/api/cultural-offers/%d/subscriptions", EXISTING_SUBSCRIPTION_ID_FOR_USER_2),
                HttpMethod.DELETE,
                null,
                Void.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }


    @Test
    public void givenNonRegularUserIsLoggedInThenSubscriptionsDeleteWillReturnForbidden() {
        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<SubcategoryRequest> entity =
                new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                String.format("/api/cultural-offers/%d/subscriptions", EXISTING_SUBSCRIPTION_ID_FOR_USER_2),
                HttpMethod.DELETE,
                entity,
                Void.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void givenInvalidCulturalOfferIdThenSubscriptionsDeleteWillReturnBadRequest() {
        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_REGULAR_USER_EMAIL2, EXISTING_REGULAR_USER_PASSWORD2);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<SubcategoryRequest> entity =
                new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                String.format("/api/cultural-offers/%d/subscriptions", INVALID_CULTURAL_OFFER_ID),
                HttpMethod.DELETE,
                entity,
                Void.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void givenSubscriptionAlreadyExistsThenSubscriptionsDeleteWillReturnNotFound() {
        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_REGULAR_USER_EMAIL2, EXISTING_REGULAR_USER_PASSWORD2);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<SubcategoryRequest> entity =
                new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                String.format("/api/cultural-offers/%d/subscriptions", NON_EXISTING_SUBSCRIPTION_ID_FOR_USER_2),
                HttpMethod.DELETE,
                entity,
                Void.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    // getLocations()

    @Test
    public void givenValidQueryParamsGetCulturalOfferLocationsWillReturnNotEmpty() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/api/cultural-offers/locations")
                .queryParam("latitudeFrom", EXISTING_LATITUDE_RANGE_FROM)
                .queryParam("latitudeTo", EXISTING_LATITUDE_RANGE_TO)
                .queryParam("longitudeFrom", EXISTING_LONGITUDE_RANGE_FROM)
                .queryParam("longitudeTo", EXISTING_LONGITUDE_RANGE_TO)
                .queryParam("categoryId", EXISTING_CATEGORY_ID)
                .queryParam("subcategoryId", EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1);

        ResponseEntity<List<GetCulturalOfferLocationsQueryHandler.GetCulturalOfferLocationsDTO>> response =
                restTemplate.exchange(
                        builder.toUriString(),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {}
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(EXISTING_CULTURAL_OFFERS_FOR_SUBCATEGORY_1_1, response.getBody().size());
    }

    @Test
    public void givenInvalidQueryParamsGetCulturalOfferLocationsWillReturnBadRequest() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/api/cultural-offers/locations")
                .queryParam("latitudeFrom", INVALID_LATITUDE_RANGE_FROM)
                .queryParam("latitudeTo", INVALID_LATITUDE_RANGE_TO)
                .queryParam("longitudeFrom", EXISTING_LONGITUDE_RANGE_FROM)
                .queryParam("longitudeTo", EXISTING_LONGITUDE_RANGE_TO)
                .queryParam("categoryId", EXISTING_CATEGORY_ID)
                .queryParam("subcategoryId", EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1);

        ResponseEntity<Object> response =
                restTemplate.exchange(
                        builder.toUriString(),
                        HttpMethod.GET,
                        null,
                        Object.class
                );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void givenCategoryDoesntExistGetCulturalOfferLocationsWillReturnNotFound() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/api/cultural-offers/locations")
                .queryParam("latitudeFrom", EXISTING_LATITUDE_RANGE_FROM)
                .queryParam("latitudeTo", EXISTING_LATITUDE_RANGE_TO)
                .queryParam("longitudeFrom", EXISTING_LONGITUDE_RANGE_FROM)
                .queryParam("longitudeTo", EXISTING_LONGITUDE_RANGE_TO)
                .queryParam("categoryId", NON_EXISTING_CATEGORY_ID);

        ResponseEntity<Object> response =
                restTemplate.exchange(
                        builder.toUriString(),
                        HttpMethod.GET,
                        null,
                        Object.class
                );


        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void givenSubcategoryDoesntExistGetCulturalOfferLocationsWillReturnNotFound() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/api/cultural-offers/locations")
                .queryParam("latitudeFrom", EXISTING_LATITUDE_RANGE_FROM)
                .queryParam("latitudeTo", EXISTING_LATITUDE_RANGE_TO)
                .queryParam("longitudeFrom", EXISTING_LONGITUDE_RANGE_FROM)
                .queryParam("longitudeTo", EXISTING_LONGITUDE_RANGE_TO)
                .queryParam("categoryId", EXISTING_CATEGORY_ID)
                .queryParam("subcategoryId", NON_EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1);

        ResponseEntity<Object> response =
                restTemplate.exchange(
                        builder.toUriString(),
                        HttpMethod.GET,
                        null,
                        Object.class
                );


        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}