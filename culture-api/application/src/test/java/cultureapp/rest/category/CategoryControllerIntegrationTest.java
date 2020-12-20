package cultureapp.rest.category;

import static cultureapp.common.CategoryTestData.*;
import static cultureapp.common.AuthenticationTestData.*;
import static org.junit.Assert.*;

import cultureapp.domain.category.Category;
import cultureapp.domain.category.CategoryRepository;
import cultureapp.domain.category.query.GetCategoriesQuery;
import cultureapp.domain.category.query.GetCategoryByIdQuery;
import cultureapp.rest.ControllerIntegrationTestUtil;
import cultureapp.rest.core.PaginatedResponse;
import cultureapp.rest.subcategory.SubcategoryRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.core.ParameterizedTypeReference;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CategoryControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CategoryRepository categoryRepository;


    /*
    -----------------------
    POST
    -----------------------
    */
    @Test
    public void givenAdminIsLoggedInANdRequestIsValidThenCategoryPostWillSucceed() {
        long categoryCount = categoryRepository.count();

        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<CategoryRequest> entity =
                new HttpEntity<>(new CategoryRequest(NON_EXISTING_CATEGORY_NAME), headers);

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/categories"),
                entity,
                Void.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(categoryCount + 1, categoryRepository.count());

        Category category = categoryRepository.findAll().get((int) categoryCount);
        assertEquals(NON_EXISTING_CATEGORY_NAME, category.getName());
        categoryRepository.delete(category);
    }

    @Test
    public void givenUnauthenticatedThenCategoryPostWillReturnUnauthorized() {
        long categoryCount = categoryRepository.count();

        HttpEntity<CategoryRequest> entity =
                new HttpEntity<>(new CategoryRequest());

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/categories"),
                entity,
                Void.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(categoryCount, categoryRepository.count());
    }

    @Test
    public void givenNonAdminLoggedInThenPostWillReturnForbidden() {
        long categoryCount = categoryRepository.count();

        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<CategoryRequest> entity =
                new HttpEntity<>(new CategoryRequest(NON_EXISTING_CATEGORY_NAME), headers);

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/categories"),
                entity,
                Void.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals(categoryCount, categoryRepository.count());
    }

    @Test
    public void givenInvalidCategoryNameThenCategoryPostWillReturnBadRequest() {
        long categoryCount = categoryRepository.count();

        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<CategoryRequest> entity =
                new HttpEntity<>(new CategoryRequest(INVALID_CATEGORY_NAME), headers);

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/categories"),
                entity,
                Void.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(categoryCount, categoryRepository.count());
    }

    @Test
    public void givenExistingCategoryNameThenCategoryWillReturnConflict() {
        long categoryCount = categoryRepository.count();

        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<SubcategoryRequest> entity =
                new HttpEntity<>(new SubcategoryRequest(EXISTING_CATEGORY_NAME_FOR_ID_1), headers);

        ResponseEntity<Void> response = restTemplate.postForEntity(
                String.format("/api/categories"),
                entity,
                Void.class);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(categoryCount, categoryRepository.count());
    }

    /*
    -----------------------
    GET
    -----------------------
    */

    @Test
    public void givenFirstPageThenCategoriesGetWillReturnNonEmpty() {
        ResponseEntity<PaginatedResponse<GetCategoriesQuery.GetCategoriesDTO>> response =
                restTemplate.exchange(
                        String.format("/api/categories?page=%d&limit=%d",
                                CATEGORY_FIRST_PAGE,
                                CATEGORY_PAGE_SIZE),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());

        assertEquals(CATEGORY_PAGE_SIZE, response.getBody().getData().size());
        assertTrue(response.getBody().getLinks().containsKey("self"));
        assertTrue(response.getBody().getLinks().containsKey("next"));
        assertFalse(response.getBody().getLinks().containsKey("prev"));
    }

    @Test
    public void givenLastPageThenCategoriesGetWillReturnNonEmpty() {
        ResponseEntity<PaginatedResponse<GetCategoriesQuery.GetCategoriesDTO>> response =
                restTemplate.exchange(
                        String.format("/api/categories?page=%d&limit=%d",
                                CATEGORY_LAST_PAGE,
                                CATEGORY_PAGE_SIZE),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());

        assertEquals(CATEGORY_PAGE_SIZE, response.getBody().getData().size());
        assertTrue(response.getBody().getLinks().containsKey("self"));
        assertFalse(response.getBody().getLinks().containsKey("next"));
        assertTrue(response.getBody().getLinks().containsKey("prev"));
    }

    @Test
    public void givenPageDoesntExistThenCategoriesGetWillReturnEmpty() {
        ResponseEntity<PaginatedResponse<GetCategoriesQuery.GetCategoriesDTO>> response =
                restTemplate.exchange(
                        String.format("/api/categories?page=10&limit=%d",
                                CATEGORY_PAGE_SIZE),
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
    public void givenValidCategoryIdThenCategoryGetWillSucceed() {
        ResponseEntity<GetCategoryByIdQuery.GetCategoryByIdDTO> response =
                restTemplate.exchange(
                        String.format("/api/categories/%d",
                                EXISTING_CATEGORY_ID),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {});

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(EXISTING_CATEGORY_ID, response.getBody().getId());
        assertEquals(EXISTING_CATEGORY_NAME_FOR_ID_1, response.getBody().getName());
    }

    @Test
    public void givenCategoryDoesntExistThenCategoryGetWillReturnNotFound() {
        ResponseEntity<PaginatedResponse<GetCategoriesQuery.GetCategoriesDTO>> response =
                restTemplate.exchange(
                        String.format("/api/categories/%d",
                                NON_EXISTING_CATEGORY_ID),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /*
    -----------------------
    UPDATE
    -----------------------
    */

    @Test
    public void givenValidRequestBodyThenCategoryUpdateWillSucceed() {
        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<CategoryRequest> entity =
                new HttpEntity<>(new CategoryRequest(NON_EXISTING_CATEGORY_NAME), headers);

        ResponseEntity<Void> response =
                restTemplate.exchange(
                        String.format("/api/categories/%d",
                                EXISTING_CATEGORY_ID),
                        HttpMethod.PUT,
                        entity,
                        new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        Optional<Category> categoryOptional = categoryRepository.findByIdAndArchivedFalse(EXISTING_CATEGORY_ID);
        assertTrue(categoryOptional.isPresent());

        Category category = categoryOptional.get();
        assertEquals(NON_EXISTING_CATEGORY_NAME, category.getName());
        assertEquals(EXISTING_CATEGORY_ID, category.getId());

        category.update(EXISTING_CATEGORY_NAME_FOR_ID_1);
        categoryRepository.save(category);
    }

    @Test
    public void givenInvalidRequestThenCategoryUpdateWillReturnBadRequest() {
        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<CategoryRequest> entity =
                new HttpEntity<>(new CategoryRequest(INVALID_CATEGORY_NAME), headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                String.format("/api/categories/%d", EXISTING_CATEGORY_ID),
                HttpMethod.PUT,
                entity,
                Void.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void givenCategoryNameExistThenCategoryUpdateWillReturnConflict() {
        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<CategoryRequest> entity =
                new HttpEntity<>(new CategoryRequest(EXISTING_CATEGORY_NAME_FOR_ID_2), headers);

        ResponseEntity<Void> response =
                restTemplate.exchange(
                        String.format("/api/categories/%d", EXISTING_CATEGORY_ID),
                        HttpMethod.PUT,
                        entity,
                        Void.class);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void givenUnauthenticatedThenCategoryUpdateWillReturnUnauthorized() {
        HttpEntity<CategoryRequest> entity =
                new HttpEntity<>(new CategoryRequest(EXISTING_CATEGORY_NAME_FOR_ID_1));

        ResponseEntity<Void> response =
                restTemplate.exchange(
                        String.format("/api/categories/%d",
                                EXISTING_CATEGORY_ID),
                        HttpMethod.PUT,
                        entity,
                        new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void givenNonAdminIsLoggedInThenCategoryUpdateWillReturnForbidden() {
        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<CategoryRequest> entity =
                new HttpEntity<>(new CategoryRequest(EXISTING_CATEGORY_NAME_FOR_ID_1), headers);

        ResponseEntity<Void> response =
                restTemplate.exchange(
                        String.format("/api/categories/%d",
                                EXISTING_CATEGORY_ID),
                        HttpMethod.PUT,
                        entity,
                        new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

     /*
    -----------------------
    DELETE
    -----------------------
    */

    @Test
    public void givenCategoryExistsThenCategoryDeleteWillSucceed() {
        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<CategoryRequest> entity =
                new HttpEntity<>(new CategoryRequest(EXISTING_CATEGORY_NAME_FOR_ID_1), headers);

        ResponseEntity<Void> response =
                restTemplate.exchange(
                        String.format("/api/categories/%d", EXISTING_CATEGORY_ID_WITHOUT_SUBCATEGORY),
                        HttpMethod.DELETE,
                        entity,
                        new ParameterizedTypeReference<>() {});
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        Optional<Category> categoryOptional = categoryRepository.findByIdAndArchivedFalse(EXISTING_CATEGORY_ID_WITHOUT_SUBCATEGORY);
        assertTrue(categoryOptional.isEmpty());

        Category category = categoryRepository.findById(EXISTING_CATEGORY_ID_WITHOUT_SUBCATEGORY).orElseThrow();
        category.unarchive();
        categoryRepository.save(category);
    }

    @Test
    public void givenUnauthenticatedInThenCategoryDeleteWillReturnUnauthorized() {
        HttpEntity<CategoryRequest> entity =
                new HttpEntity<>(new CategoryRequest(EXISTING_CATEGORY_NAME_FOR_ID_1));

        ResponseEntity<Void> response =
                restTemplate.exchange(
                        String.format("/api/categories/%d",
                                EXISTING_CATEGORY_ID),
                        HttpMethod.DELETE,
                        entity,
                        new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void givenNonAdminIsLoggedInThenCategoryDeleteWillReturnForbidden() {
        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<CategoryRequest> entity =
                new HttpEntity<>(new CategoryRequest(EXISTING_CATEGORY_NAME_FOR_ID_1), headers);

        ResponseEntity<Void> response =
                restTemplate.exchange(
                        String.format("/api/categories/%d",
                                EXISTING_CATEGORY_ID),
                        HttpMethod.DELETE,
                        entity,
                        new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
}
