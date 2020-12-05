package cultureapp.domain.category;

import static org.assertj.core.api.Assertions.assertThat;
import cultureapp.domain.category.command.AddCategoryUseCase;
import cultureapp.domain.category.command.UpdateCategoryUseCase;
import cultureapp.domain.category.exception.CategoryAlreadyExists;
import cultureapp.domain.category.exception.CategoryNotFoundException;
import cultureapp.domain.category.query.GetCategoriesQuery;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.*;

import javax.validation.ConstraintViolationException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;

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
    public void whenAddCategoryCommandIsValidThenAddCategoryWillSucceed() throws CategoryAlreadyExists {
        String name = "Category";
        AddCategoryUseCase.AddCategoryCommand command =
                new AddCategoryUseCase.AddCategoryCommand(name);

        categoryService.addCategory(command);

        Mockito.verify(categoryRepository, times(1)).save(notNull());
    }

    @Test(expected = ConstraintViolationException.class)
    public void whenAddCategoryCommandNameIsEmptyOrNullThenCreateCategoryCommandWillFail() {
        String name = "";
        new AddCategoryUseCase.AddCategoryCommand(name);

        name = null;
        new AddCategoryUseCase.AddCategoryCommand(name);
    }

    @Test(expected = Exception.class)
    public void whenAddCategoryCommandExistingNameThenCreateCategoryCommandWillFail() throws CategoryAlreadyExists {
        String name = "Institution";
        AddCategoryUseCase.AddCategoryCommand command =
                new AddCategoryUseCase.AddCategoryCommand(name);
        given(categoryRepository.save(Category.of(name))).willThrow(new CategoryAlreadyExists(name));

        categoryService.addCategory(command);

        Mockito.verify(categoryRepository, times(1)).save(notNull());
    }

    /*
    -----------------------
    GET
    -----------------------
    */
    @Test
    public void whenCategoryIdExistsThenGetCategorySucceed() throws CategoryNotFoundException {
        Long id = 1L;
        String name = "Category";
        Category category = Category.withId(id, name);

        given(categoryRepository.findByIdAndArchivedFalse(id)).willReturn(Optional.of(category));

        categoryService.getCategory(id);
    }

    @Test(expected = CategoryNotFoundException.class)
    public void whenCategoryIdIsInvalidThenGetCategoryFail() throws CategoryNotFoundException {
        Long id = -1L;

        given(categoryRepository.findByIdAndArchivedFalse(id)).willReturn(Optional.empty());

        categoryService.getCategory(id);
    }

    @Test
    public void whenGetCategoriesExistThenGetCategoriesSucceed() {
        int page = 0;
        int limit = 3;

        given(categoryRepository.findAllByArchivedFalse(PageRequest.of(page, limit, Sort.by("name")))).willReturn(
                new SliceImpl<>(List.of(Category.of("Category1"), Category.of("Category2"))));
        Slice<GetCategoriesQuery.GetCategoriesDTO> categories = categoryService.getCategories(page, limit);

        assertThat(categories.getContent().size()).isEqualTo(2);
        assertThat(categories.getContent().size()).isNotZero();
    }

    @Test
    public void whenCategoriesEmptyThenGetCategoriesSucceed() {
        int page = 0;
        int limit = 3;

        given(categoryRepository.findAllByArchivedFalse(PageRequest.of(page, limit, Sort.by("name")))).willReturn(
                new SliceImpl<>(Collections.emptyList()));
        Slice<GetCategoriesQuery.GetCategoriesDTO> categories = categoryService.getCategories(page, limit);

        assertThat(categories.getContent().size()).isZero();
    }

    /*
    -----------------------
    PUT
    -----------------------
    */

    @Test
    public void whenUpdateCategoryCommandIsValidThenUpdateCategorySucceed() throws CategoryNotFoundException, CategoryAlreadyExists {
        Long id = 1L;
        String name = "Category";
        String updatingName = "UpdatedCategory";
        UpdateCategoryUseCase.UpdateCategoryCommand command =
                new UpdateCategoryUseCase.UpdateCategoryCommand(id, updatingName);
        Category category = Category.withId(id, name);
        given(categoryRepository.findByIdAndArchivedFalse(id)).willReturn(Optional.of(category));

        categoryService.updateCategory(command);

        assertThat(category.getName()).isNotBlank();
        assertThat(category.getName()).isEqualTo(updatingName);
    }

    @Test(expected = Exception.class)
    public void whenUpdateCategoryCommandAlreadyExistsThenUpdateCategoryFail() throws CategoryNotFoundException, CategoryAlreadyExists {
        Long id = 1L;
        String name = "Institution";
        UpdateCategoryUseCase.UpdateCategoryCommand command =
                new UpdateCategoryUseCase.UpdateCategoryCommand(id, name);
        given(categoryRepository.findByIdAndArchivedFalse(id)).willReturn(Optional.empty());

        categoryService.updateCategory(command);
    }

    /*
    -----------------------
    DELETE
    -----------------------
    */

    @Test
    public void whenDeleteCategoryCommandWithValidIdThenDeletingCategorySucceed() throws CategoryNotFoundException {
        Long id = 1L;
        String name = "Category";
        Category category = Category.withId(id, name);
        given(categoryRepository.findByIdAndArchivedFalse(id)).willReturn(Optional.of(category));

        categoryService.deleteCategoryById(id);

        assertThat(category.isArchived()).isTrue();
    }

    @Test(expected = CategoryNotFoundException.class)
    public void whenDeleteCategoryCommandWithInvalidIdThenDeletingCategoryFail() throws CategoryNotFoundException {
        Long id = -1L;
        String name = "Category";
        Category category = Category.withId(id, name);
        given(categoryRepository.findByIdAndArchivedFalse(id)).willReturn(Optional.empty());

        categoryService.deleteCategoryById(id);
    }
}
