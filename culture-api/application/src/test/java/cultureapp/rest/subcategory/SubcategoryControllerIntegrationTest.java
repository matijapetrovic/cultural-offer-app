package cultureapp.rest.subcategory;

import static cultureapp.common.SubcategoryTestData.*;
import static cultureapp.common.CategoryTestData.*;
import static cultureapp.common.AuthenticationTestData.*;

import cultureapp.domain.subcategory.Subcategory;
import cultureapp.domain.subcategory.SubcategoryRepository;
import cultureapp.domain.subcategory.query.GetSubcategoriesQuery;
import cultureapp.rest.ControllerIntegrationTestUtil;
import cultureapp.rest.core.PaginatedResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Slice;
import org.springframework.http.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.core.ParameterizedTypeReference;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class SubcategoryControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    @Test
    public void givenUnauthenticatedInThenSubcategoryPostWillReturnUnauthorized() {
        long subcategoryCount = subcategoryRepository.count();

        HttpEntity<SubcategoryRequest> entity =
                new HttpEntity<>(new SubcategoryRequest(NON_EXISTING_SUBCATEGORY_NAME_FOR_CATEGORY_ID_1));

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/categories/%d/subcategories", EXISTING_CATEGORY_ID),
                entity,
                Void.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(subcategoryCount, subcategoryRepository.count());
    }

    @Test
    public void givenRegularUserIsLoggedInThenSubcategoryPostWillReturnForbidden() {
        long subcategoryCount = subcategoryRepository.count();

        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<SubcategoryRequest> entity =
                new HttpEntity<>(new SubcategoryRequest(NON_EXISTING_SUBCATEGORY_NAME_FOR_CATEGORY_ID_1), headers);

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/categories/%d/subcategories", EXISTING_CATEGORY_ID),
                entity,
                Void.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals(subcategoryCount, subcategoryRepository.count());
    }

    @Test
    public void givenCategoryDoesntExistThenSubcategoryPostWillReturnNotFound() {
        long subcategoryCount = subcategoryRepository.count();

        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<SubcategoryRequest> entity =
                new HttpEntity<>(new SubcategoryRequest(NON_EXISTING_SUBCATEGORY_NAME_FOR_CATEGORY_ID_1), headers);

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/categories/%d/subcategories", NON_EXISTING_CATEGORY_ID),
                entity,
                Void.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(subcategoryCount, subcategoryRepository.count());
    }

    @Test
    public void givenInvalidSubcategoryNameThenSubcategoryPostWillReturnBadRequest() {
        long subcategoryCount = subcategoryRepository.count();

        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<SubcategoryRequest> entity =
                new HttpEntity<>(new SubcategoryRequest(INVALID_SUBCATEGORY_NAME), headers);

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/categories/%d/subcategories", EXISTING_CATEGORY_ID),
                entity,
                Void.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(subcategoryCount, subcategoryRepository.count());
    }

    @Test
    public void givenExistingSubcategoryNameThenSubcategoryPostWillReturnConflict() {
        long subcategoryCount = subcategoryRepository.count();

        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<SubcategoryRequest> entity =
                new HttpEntity<>(new SubcategoryRequest(EXISTING_SUBCATEGORY_NAME_FOR_CATEGORY_ID_1), headers);

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/categories/%d/subcategories", EXISTING_CATEGORY_ID),
                entity,
                Void.class);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(subcategoryCount, subcategoryRepository.count());
    }

    @Test
    @Transactional
    @Rollback
    public void givenAdminIsLoggedInAndRequestIsValidThenSubcategoryPostWillSucceed() {
        long subcategoryCount = subcategoryRepository.count();

        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<SubcategoryRequest> entity =
                new HttpEntity<>(new SubcategoryRequest(NON_EXISTING_SUBCATEGORY_NAME_FOR_CATEGORY_ID_1), headers);

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/categories/%d/subcategories", EXISTING_CATEGORY_ID),
                entity,
                Void.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(subcategoryCount + 1, subcategoryRepository.count());

        Subcategory subcategory = subcategoryRepository.findAll().get((int) subcategoryCount);
        assertEquals(NON_EXISTING_SUBCATEGORY_NAME_FOR_CATEGORY_ID_1, subcategory.getName());
        assertEquals(EXISTING_CATEGORY_ID, subcategory.getCategory().getId());
    }

    @Test
    public void givenValidCategoryIdAndValidQueryParamsThenSubcategoriesGetWillReturnNonEmpty() {
        final int pageSize = 2;

        ResponseEntity<PaginatedResponse<GetSubcategoriesQuery.GetSubcategoriesDTO>> response =
                restTemplate.exchange(
                        String.format("/api/categories/%d/subcategories?page=0&limit=%d", EXISTING_CATEGORY_ID, pageSize),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());
        assertEquals(pageSize, response.getBody().getData().size());
    }

}
