package cultureapp.domain.category;

import static cultureapp.common.CategoryTestData.*;

import cultureapp.domain.category.command.AddCategoryUseCase;
import cultureapp.domain.category.command.UpdateCategoryUseCase;
import cultureapp.domain.category.exception.CategoryAlreadyExistsException;
import cultureapp.domain.category.exception.CategoryNotFoundException;
import cultureapp.domain.category.query.GetCategoriesQueryHandler;
import cultureapp.domain.category.query.GetCategoryByIdQueryHandler;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;

import javax.validation.ConstraintViolationException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.junit.Assert.*;

public class CategoryServiceUnitTest {
    private final CategoryRepository categoryRepository =
            Mockito.mock(CategoryRepository.class);

    private final CategoryService categoryService =
            new CategoryService(categoryRepository);

    /*
    -----------------------
    POST
    -----------------------
    */
    @Test
    public void givenAddCategoryCommandIsValidThenAddCategoryWillSucceed() throws CategoryAlreadyExistsException {
        AddCategoryUseCase.AddCategoryCommand command =
                new AddCategoryUseCase.AddCategoryCommand(VALID_CATEGORY_NAME);

        categoryService.addCategory(command);

       then(categoryRepository)
               .should()
               .save(notNull());
    }

    @Test(expected = ConstraintViolationException.class)
    public void givenAddCategoryCommandNameIsEmptyOrNullThenCreateCategoryCommandWillFail() {
        new AddCategoryUseCase.AddCategoryCommand(INVALID_CATEGORY_NAME);
    }

    @Test(expected = CategoryAlreadyExistsException.class)
    public void givenCategoryNameAlreadyExistingNameThenCreateCategoryCommandWillFail() throws CategoryAlreadyExistsException {
        AddCategoryUseCase.AddCategoryCommand command =
                new AddCategoryUseCase.AddCategoryCommand(EXISTING_CATEGORY_NAME_FOR_ID_1);
        given(categoryRepository.save(notNull())).willThrow(DataIntegrityViolationException.class);

        categoryService.addCategory(command);
    }

    /*
    -----------------------
    GET
    -----------------------
    */
    @Test
    public void givenCategoryIdExistsThenGetCategoryWillReturnNotEmpty() throws CategoryNotFoundException {
        Category category = Category.withId(VALID_CATEGORY_ID, VALID_CATEGORY_NAME);

        given(categoryRepository.findByIdAndArchivedFalse(VALID_CATEGORY_ID)).willReturn(Optional.of(category));

        GetCategoryByIdQueryHandler.GetCategoryByIdQuery query =
                new GetCategoryByIdQueryHandler.GetCategoryByIdQuery(VALID_CATEGORY_ID);

        categoryService.handleGetCategory(query);
    }

    @Test(expected = CategoryNotFoundException.class)
    public void givenCategoryIdIsInvalidThenGetCategoryWillFail() throws CategoryNotFoundException {
        given(categoryRepository.findByIdAndArchivedFalse(VALID_CATEGORY_ID)).willReturn(Optional.empty());

        GetCategoryByIdQueryHandler.GetCategoryByIdQuery query =
                new GetCategoryByIdQueryHandler.GetCategoryByIdQuery(VALID_CATEGORY_ID);

        categoryService.handleGetCategory(query);
    }

    @Test
    public void givenGetCategoriesExistThenGetCategoriesSucceed() {
        int page = 0;

        GetCategoriesQueryHandler.GetCategoriesQuery query =
                new GetCategoriesQueryHandler.GetCategoriesQuery(page, CATEGORY_PAGE_SIZE);

        given(categoryRepository.findAllByArchivedFalse(PageRequest.of(page, CATEGORY_PAGE_SIZE, Sort.by("name")))).willReturn(
                new SliceImpl<>(List.of(Category.of("Category1"), Category.of("Category2"))));
        Slice<GetCategoriesQueryHandler.GetCategoriesDTO> categories = categoryService.handleGetCategories(query);

        assertEquals(categories.getContent().size(), 2);
        assertNotEquals(categories.getContent().size(), 0);
    }

    @Test
    public void givenCategoriesEmptyThenGetCategoriesSucceed() {
        int page = 0;
        int limit = 3;

        GetCategoriesQueryHandler.GetCategoriesQuery query =
                new GetCategoriesQueryHandler.GetCategoriesQuery(page, limit);

        given(categoryRepository.findAllByArchivedFalse(PageRequest.of(page, limit, Sort.by("name")))).willReturn(
                new SliceImpl<>(Collections.emptyList()));
        Slice<GetCategoriesQueryHandler.GetCategoriesDTO> categories = categoryService.handleGetCategories(query);

        assertEquals(categories.getContent().size(), 0);
    }

    /*
    -----------------------
    PUT
    -----------------------
    */

    @Test
    public void givenUpdateCategoryCommandIsValidThenUpdateCategorySucceed() throws CategoryNotFoundException, CategoryAlreadyExistsException {
        String updatingName = "UpdatedCategory";
        UpdateCategoryUseCase.UpdateCategoryCommand command =
                new UpdateCategoryUseCase.UpdateCategoryCommand(VALID_CATEGORY_ID, updatingName);
        Category category = Category.withId(VALID_CATEGORY_ID, VALID_CATEGORY_NAME);
        given(categoryRepository.findByIdAndArchivedFalse(VALID_CATEGORY_ID)).willReturn(Optional.of(category));

        categoryService.updateCategory(command);

        assertNotNull(category.getName());
        assertEquals(category.getName(), updatingName);
    }

    @Test(expected = Exception.class)
    public void givenUpdateCategoryCommandAlreadyExistsThenUpdateCategoryWillFail() throws CategoryNotFoundException, CategoryAlreadyExistsException {
        UpdateCategoryUseCase.UpdateCategoryCommand command =
                new UpdateCategoryUseCase.UpdateCategoryCommand(NON_EXISTING_CATEGORY_ID, VALID_CATEGORY_NAME);
        given(categoryRepository.findByIdAndArchivedFalse(NON_EXISTING_CATEGORY_ID)).willThrow(CategoryNotFoundException.class);

        categoryService.updateCategory(command);
    }

    /*
    -----------------------
    DELETE
    -----------------------
    */

    @Test
    public void givenDeleteCategoryCommandWithValidIdThenDeletingCategorySucceed() throws CategoryNotFoundException {
        Category category = Category.withId(VALID_CATEGORY_ID, VALID_CATEGORY_NAME);
        given(categoryRepository.findByIdAndArchivedFalse(VALID_CATEGORY_ID)).willReturn(Optional.of(category));

        categoryService.deleteCategoryById(VALID_CATEGORY_ID);

        assertTrue(category.isArchived());
    }

    @Test(expected = CategoryNotFoundException.class)
    public void givenDeleteCategoryCommandWithInvalidIdThenDeletingCategoryWillFail() throws CategoryNotFoundException {
        given(categoryRepository.findByIdAndArchivedFalse(INVALID_CATEGORY_ID)).willReturn(Optional.empty());

        categoryService.deleteCategoryById(INVALID_CATEGORY_ID);
    }
}
