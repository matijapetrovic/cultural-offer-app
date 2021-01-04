package cultureapp.rest.subcategory;

import static cultureapp.common.SubcategoryTestData.*;
import static cultureapp.common.CategoryTestData.*;
import static cultureapp.common.AuthenticationTestData.*;

import cultureapp.domain.subcategory.Subcategory;
import cultureapp.domain.subcategory.SubcategoryId;
import cultureapp.domain.subcategory.SubcategoryRepository;
import cultureapp.domain.subcategory.query.GetSubcategoriesQueryHandler;
import cultureapp.domain.subcategory.query.GetSubcategoryByIdQueryHandler;
import cultureapp.rest.ControllerIntegrationTestUtil;
import cultureapp.rest.core.PaginatedResponse;
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
        subcategoryRepository.delete(subcategory);
    }

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
    public void givenNonAdminIsLoggedInThenSubcategoryPostWillReturnForbidden() {
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

    // getPage()

    @Test
    public void givenValidCategoryIdAndFirstPageThenSubcategoriesGetWillReturnNonEmpty() {
        ResponseEntity<PaginatedResponse<GetSubcategoriesQueryHandler.GetSubcategoriesDTO>> response =
                restTemplate.exchange(
                        String.format("/api/categories/%d/subcategories?page=%d&limit=%d",
                                EXISTING_CATEGORY_ID,
                                FIRST_PAGE_FOR_CATEGORY_ID_1,
                                SUBCATEGORY_PAGE_SIZE),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());

        assertEquals(FIRST_PAGE_NUM_SUBCATEGORIES_FOR_CATEGORY_ID_1, response.getBody().getData().size());
        assertTrue(response.getBody().getLinks().containsKey("self"));
        assertTrue(response.getBody().getLinks().containsKey("next"));
        assertFalse(response.getBody().getLinks().containsKey("prev"));
    }

    @Test
    public void givenValidCategoryIdAndLastPageThenSubcategoriesGetWillReturnNonEmpty() {
        ResponseEntity<PaginatedResponse<GetSubcategoriesQueryHandler.GetSubcategoriesDTO>> response =
                restTemplate.exchange(
                        String.format("/api/categories/%d/subcategories?page=%d&limit=%d",
                                EXISTING_CATEGORY_ID,
                                LAST_PAGE_FOR_CATEGORY_ID_1,
                                SUBCATEGORY_PAGE_SIZE),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());

        assertEquals(LAST_PAGE_NUM_SUBCATEGORIES_FOR_CATEGORY_ID_1, response.getBody().getData().size());
        assertTrue(response.getBody().getLinks().containsKey("self"));
        assertFalse(response.getBody().getLinks().containsKey("next"));
        assertTrue(response.getBody().getLinks().containsKey("prev"));
    }

    @Test
    public void givenMissingQueryParamsThenSubcategoriesGetWillReturnBadRequest() {
        ResponseEntity<PaginatedResponse<GetSubcategoriesQueryHandler.GetSubcategoriesDTO>> response =
                restTemplate.exchange(
                        String.format("/api/categories/%d/subcategories", EXISTING_CATEGORY_ID),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void givenCategoryDoesntExistThenSubcategoriesGetWillReturnNotFound() {
        ResponseEntity<PaginatedResponse<GetSubcategoriesQueryHandler.GetSubcategoriesDTO>> response =
                restTemplate.exchange(
                        String.format("/api/categories/%d/subcategories/?page=0&limit=2", NON_EXISTING_CATEGORY_ID),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void givenPageDoesntExistThenSubcategoriesGetWillReturnEmpty() {
        ResponseEntity<PaginatedResponse<GetSubcategoriesQueryHandler.GetSubcategoriesDTO>> response =
                restTemplate.exchange(
                        String.format("/api/categories/%d/subcategories?page=10&limit=2", EXISTING_CATEGORY_ID),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());
        assertEquals(0, response.getBody().getData().size());
    }


    // getSingle()

    @Test
    public void givenValidCategoryIdAndValidSubcategoryIdThenSubcategoryGetWillSucceed() {
        ResponseEntity<GetSubcategoryByIdQueryHandler.GetSubcategoryByIdDTO> response =
                restTemplate.exchange(
                        String.format(
                                "/api/categories/%d/subcategories/%d",
                                EXISTING_CATEGORY_ID,
                                EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1),
                        HttpMethod.GET,
                        null,
                        GetSubcategoryByIdQueryHandler.GetSubcategoryByIdDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1, response.getBody().getId());
        assertEquals(EXISTING_CATEGORY_ID, response.getBody().getCategoryId());
        assertEquals(EXISTING_SUBCATEGORY_NAME_FOR_CATEGORY_ID_1, response.getBody().getName());
    }

    @Test
    public void givenSubcategoryDoesntExistThenSubcategoryGetWillReturnNotFound() {
        ResponseEntity<GetSubcategoryByIdQueryHandler.GetSubcategoryByIdDTO> response =
                restTemplate.exchange(
                        String.format(
                                "/api/categories/%d/subcategories/%d",
                                EXISTING_CATEGORY_ID,
                                NON_EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1),
                        HttpMethod.GET,
                        null,
                        GetSubcategoryByIdQueryHandler.GetSubcategoryByIdDTO.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void givenCategoryDoesntExistThenSubcategoryGetWillReturnNotFound() {
        ResponseEntity<GetSubcategoryByIdQueryHandler.GetSubcategoryByIdDTO> response =
                restTemplate.exchange(
                        String.format(
                                "/api/categories/%d/subcategories/%d",
                                NON_EXISTING_CATEGORY_ID,
                                EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1),
                        HttpMethod.GET,
                        null,
                        GetSubcategoryByIdQueryHandler.GetSubcategoryByIdDTO.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // update()

    @Test
    public void givenSubcategoryExistsAndValidRequestBodyThenSubcategoryUpdateWillSucceed() {
        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<SubcategoryRequest> entity =
                new HttpEntity<>(new SubcategoryRequest(NON_EXISTING_SUBCATEGORY_NAME_FOR_CATEGORY_ID_1), headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                String.format("/api/categories/%d/subcategories/%d", EXISTING_CATEGORY_ID, EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1),
                HttpMethod.PUT,
                entity,
                Void.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        Optional<Subcategory> subcategoryOptional =
                subcategoryRepository.findByIdAndCategoryIdAndArchivedFalse(
                        EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1, EXISTING_CATEGORY_ID);
        assertTrue(subcategoryOptional.isPresent());

        Subcategory subcategory = subcategoryOptional.get();
        assertEquals(NON_EXISTING_SUBCATEGORY_NAME_FOR_CATEGORY_ID_1, subcategory.getName());
        assertEquals(EXISTING_CATEGORY_ID, subcategory.getCategory().getId());

        subcategory.update(EXISTING_SUBCATEGORY_NAME_FOR_CATEGORY_ID_1);
        subcategoryRepository.save(subcategory);
    }

    @Test
    public void givenSubcategoryExistsAndInvalidRequestBodyThenSubcategoryUpdateWillReturnBadRequest() {
        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<SubcategoryRequest> entity =
                new HttpEntity<>(new SubcategoryRequest(INVALID_SUBCATEGORY_NAME), headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                String.format("/api/categories/%d/subcategories/%d", EXISTING_CATEGORY_ID, EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1),
                HttpMethod.PUT,
                entity,
                Void.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void givenSubcategoryExistsAndNewSubcategoryNameExistsThenSubcategoryUpdateWillReturnConflict() {
        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<SubcategoryRequest> entity =
                new HttpEntity<>(new SubcategoryRequest(EXISTING_SUBCATEGORY_NAME_2_FOR_CATEGORY_ID_1), headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                String.format("/api/categories/%d/subcategories/%d", EXISTING_CATEGORY_ID, EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1),
                HttpMethod.PUT,
                entity,
                Void.class);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void givenUnauthenticatedInThenSubcategoryUpdateWillReturnUnauthorized() {
        HttpEntity<SubcategoryRequest> entity =
                new HttpEntity<>(new SubcategoryRequest(EXISTING_SUBCATEGORY_NAME_2_FOR_CATEGORY_ID_1));

        ResponseEntity<Void> response = restTemplate.exchange(
                String.format("/api/categories/%d/subcategories/%d", EXISTING_CATEGORY_ID, EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1),
                HttpMethod.PUT,
                entity,
                Void.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void givenNonAdminIsLoggedInThenSubcategoryUpdateWillReturnForbidden() {
        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<SubcategoryRequest> entity =
                new HttpEntity<>(new SubcategoryRequest(NON_EXISTING_SUBCATEGORY_NAME_FOR_CATEGORY_ID_1), headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                String.format("/api/categories/%d/subcategories/%d", EXISTING_CATEGORY_ID, EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1),
                HttpMethod.PUT,
                entity,
                Void.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void givenCategoryDoesntExistThenSubcategoryUpdateWillReturnNotFound() {
        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<SubcategoryRequest> entity =
                new HttpEntity<>(new SubcategoryRequest(NON_EXISTING_SUBCATEGORY_NAME_FOR_CATEGORY_ID_1), headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                String.format("/api/categories/%d/subcategories/%d", NON_EXISTING_CATEGORY_ID, EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1),
                HttpMethod.PUT,
                entity,
                Void.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void givenSubcategoryDoesntExistThenSubcategoryUpdateWillReturnNotFound() {
        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<SubcategoryRequest> entity =
                new HttpEntity<>(new SubcategoryRequest(NON_EXISTING_SUBCATEGORY_NAME_FOR_CATEGORY_ID_1), headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                String.format("/api/categories/%d/subcategories/%d", EXISTING_CATEGORY_ID, NON_EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1),
                HttpMethod.PUT,
                entity,
                Void.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // delete()

    @Test
    public void givenSubcategoryExistsThenSubcategoryDeleteWillSucceed() {
        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<SubcategoryRequest> entity =
                new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                String.format("/api/categories/%d/subcategories/%d", EXISTING_CATEGORY_ID, EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1),
                HttpMethod.DELETE,
                entity,
                Void.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        Optional<Subcategory> subcategoryOptional =
                subcategoryRepository.findByIdAndCategoryIdAndArchivedFalse(
                        EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1, EXISTING_CATEGORY_ID);
        assertTrue(subcategoryOptional.isEmpty());

        Subcategory subcategory = subcategoryRepository.findById(SubcategoryId.of(
                EXISTING_CATEGORY_ID, EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1)).orElseThrow();
        subcategory.unarchive();
        subcategoryRepository.save(subcategory);
    }

    @Test
    public void givenUnauthenticatedInThenSubcategoryDeleteWillReturnUnauthorized() {
        HttpEntity<SubcategoryRequest> entity =
                new HttpEntity<>(new SubcategoryRequest(EXISTING_SUBCATEGORY_NAME_2_FOR_CATEGORY_ID_1));

        ResponseEntity<Void> response = restTemplate.exchange(
                String.format("/api/categories/%d/subcategories/%d", EXISTING_CATEGORY_ID, EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1),
                HttpMethod.DELETE,
                entity,
                Void.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void givenNonAdminIsLoggedInThenSubcategoryDeleteWillReturnForbidden() {
        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<SubcategoryRequest> entity =
                new HttpEntity<>(new SubcategoryRequest(NON_EXISTING_SUBCATEGORY_NAME_FOR_CATEGORY_ID_1), headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                String.format("/api/categories/%d/subcategories/%d", EXISTING_CATEGORY_ID, EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1),
                HttpMethod.DELETE,
                entity,
                Void.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void givenCategoryDoesntExistThenSubcategoryDeleteWillReturnNotFound() {
        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<SubcategoryRequest> entity =
                new HttpEntity<>(new SubcategoryRequest(NON_EXISTING_SUBCATEGORY_NAME_FOR_CATEGORY_ID_1), headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                String.format("/api/categories/%d/subcategories/%d", NON_EXISTING_CATEGORY_ID, EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1),
                HttpMethod.DELETE,
                entity,
                Void.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void givenSubcategoryDoesntExistThenSubcategoryDeleteWillReturnNotFound() {
        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        HttpEntity<SubcategoryRequest> entity =
                new HttpEntity<>(new SubcategoryRequest(NON_EXISTING_SUBCATEGORY_NAME_FOR_CATEGORY_ID_1), headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                String.format("/api/categories/%d/subcategories/%d", EXISTING_CATEGORY_ID, NON_EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1),
                HttpMethod.DELETE,
                entity,
                Void.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
