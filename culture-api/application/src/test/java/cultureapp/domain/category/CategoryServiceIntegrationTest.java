package cultureapp.domain.category;

import cultureapp.domain.category.command.AddCategoryUseCase;
import cultureapp.domain.category.command.UpdateCategoryUseCase;
import cultureapp.domain.category.exception.CategoryAlreadyExistsException;
import cultureapp.domain.category.exception.CategoryCannotBeDeletedException;
import cultureapp.domain.category.exception.CategoryNotFoundException;
import cultureapp.domain.category.query.GetCategoriesQueryHandler;
import cultureapp.domain.category.query.GetCategoryByIdQueryHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static cultureapp.common.CategoryTestData.*;
import static cultureapp.common.CategoryTestData.INVALID_CATEGORY_ID;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CategoryServiceIntegrationTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    /*
    -----------------------
    POST
    -----------------------
    */

    @Test
    public void givenAddCategoryCommandIsValidThenAddCategoryWillSucceed() throws CategoryAlreadyExistsException {
        long categoryCount = categoryRepository.count();

        AddCategoryUseCase.AddCategoryCommand command =
                new AddCategoryUseCase.AddCategoryCommand(VALID_CATEGORY_NAME);
        categoryService.addCategory(command);

        assertEquals(categoryCount + 1, categoryRepository.count());

        Category category = categoryRepository.findAll().get((int) categoryCount);
        assertEquals(VALID_CATEGORY_NAME, category.getName());

        categoryRepository.delete(category);
    }

    @Test(expected = CategoryAlreadyExistsException.class)
    public void givenCategoryNameAlreadyExistingNameThenCreateCategoryCommandWillFail() throws CategoryAlreadyExistsException {
        AddCategoryUseCase.AddCategoryCommand command =
                new AddCategoryUseCase.AddCategoryCommand(EXISTING_CATEGORY_NAME_FOR_ID_1);

        categoryService.addCategory(command);
    }

    /*
    -----------------------
    GET
    -----------------------
    */
    @Test
    public void givenCategoryIdExistsThenGetCategoryWillReturnNotEmpty() throws CategoryNotFoundException {
        GetCategoryByIdQueryHandler.GetCategoryByIdQuery query =
                new GetCategoryByIdQueryHandler.GetCategoryByIdQuery(EXISTING_CATEGORY_ID);

        GetCategoryByIdQueryHandler.GetCategoryByIdDTO result =
                categoryService.handleGetCategory(query);

        assertEquals(result.getId(), EXISTING_CATEGORY_ID);
    }

    @Test(expected = ConstraintViolationException.class)
    public void givenCategoryIdIsInvalidThenGetCategoryWillFail() throws CategoryNotFoundException {
        GetCategoryByIdQueryHandler.GetCategoryByIdQuery query =
                new GetCategoryByIdQueryHandler.GetCategoryByIdQuery(INVALID_CATEGORY_ID);

        categoryService.handleGetCategory(query);
    }

    @Test
    public void givenGetCategoriesForFirstPageExistThenGetCategoriesSucceed() {
        int page  = 0;

        GetCategoriesQueryHandler.GetCategoriesQuery query =
                new GetCategoriesQueryHandler.GetCategoriesQuery(page, CATEGORY_PAGE_SIZE);

        Slice<GetCategoriesQueryHandler.GetCategoriesDTO> result = categoryService.handleGetCategories(query);

        assertEquals(result.getContent().size(), 2);
        assertNotEquals(result.getContent().size(), 0);
        assertTrue(result.hasNext());
        assertFalse(result.hasPrevious());
    }

    @Test
    public void givenGetCategoriesForLastPageExistThenGetCategoriesSucceed() {
        int page  = 1;

        GetCategoriesQueryHandler.GetCategoriesQuery query =
                new GetCategoriesQueryHandler.GetCategoriesQuery(page, CATEGORY_PAGE_SIZE);

        Slice<GetCategoriesQueryHandler.GetCategoriesDTO> result = categoryService.handleGetCategories(query);

        assertEquals(result.getContent().size(), 2);
        assertNotEquals(result.getContent().size(), 0);
        assertFalse(result.hasNext());
        assertTrue(result.hasPrevious());
    }

    /*
    -----------------------
    PUT
    -----------------------
    */

    @Test
    public void givenUpdateCategoryCommandIsValidThenUpdateCategorySucceed() throws CategoryNotFoundException, CategoryAlreadyExistsException {
        UpdateCategoryUseCase.UpdateCategoryCommand command =
                new UpdateCategoryUseCase.UpdateCategoryCommand(VALID_CATEGORY_ID, NON_EXISTING_CATEGORY_NAME);

        categoryService.updateCategory(command);

        Optional<Category> categoryOptional = categoryRepository.findByIdAndArchivedFalse(VALID_CATEGORY_ID);
        assertTrue(categoryOptional.isPresent());


        Category category = categoryOptional.get();
        assertEquals(category.getName(), NON_EXISTING_CATEGORY_NAME);
        assertEquals(category.getId(), VALID_CATEGORY_ID);

        category.update(EXISTING_CATEGORY_NAME_FOR_ID_1);
        categoryRepository.save(category);

    }

    @Test(expected = CategoryNotFoundException.class)
    public void givenUpdateCategoryCommandIdDoesNotExistThenUpdateCategoryWillFail() throws CategoryNotFoundException, CategoryAlreadyExistsException {
        UpdateCategoryUseCase.UpdateCategoryCommand command =
                new UpdateCategoryUseCase.UpdateCategoryCommand(NON_EXISTING_CATEGORY_ID, EXISTING_CATEGORY_NAME_FOR_ID_1);

        categoryService.updateCategory(command);
    }

    /*
    -----------------------
    DELETE
    -----------------------
    */

    @Test
    public void givenDeleteCategoryCommandWithValidIdThenDeletingCategorySucceed() throws CategoryNotFoundException, CategoryCannotBeDeletedException {

        categoryService.deleteCategoryById(EXISTING_CATEGORY_ID_WITHOUT_SUBCATEGORY);

        Optional<Category> categoryOptional = categoryRepository.findByIdAndArchivedFalse(EXISTING_CATEGORY_ID_WITHOUT_SUBCATEGORY);
        assertTrue(categoryOptional.isEmpty());

        Category category = categoryRepository.findById(EXISTING_CATEGORY_ID_WITHOUT_SUBCATEGORY).orElseThrow();
        category.unarchive();
        categoryRepository.save(category);
    }

    @Test(expected = CategoryNotFoundException.class)
    public void givenDeleteCategoryCommandWithInvalidIdThenDeletingCategoryWillFail() throws CategoryNotFoundException, CategoryCannotBeDeletedException {
        categoryService.deleteCategoryById(INVALID_CATEGORY_ID);
    }
}
