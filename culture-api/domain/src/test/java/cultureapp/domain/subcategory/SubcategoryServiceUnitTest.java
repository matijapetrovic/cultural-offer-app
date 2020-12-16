package cultureapp.domain.subcategory;

import cultureapp.domain.category.Category;
import cultureapp.domain.category.CategoryRepository;
import cultureapp.domain.category.exception.CategoryNotFoundException;
import cultureapp.domain.subcategory.command.AddSubcategoryUseCase;
import cultureapp.domain.subcategory.exception.SubcategoryAlreadyExistsException;
import org.junit.Test;
import org.mockito.Mockito;
import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;

public class SubcategoryServiceUnitTest {

    private final SubcategoryRepository subcategoryRepository =
            Mockito.mock(SubcategoryRepository.class);

    private final CategoryRepository categoryRepository =
            Mockito.mock(CategoryRepository.class);

    private final SubcategoryService subcategoryService =
            new SubcategoryService(subcategoryRepository, categoryRepository);

    @Test(expected = CategoryNotFoundException.class)
    public void whenCategoryDoesntExistThenAddSubcategoryWillFail() throws CategoryNotFoundException, SubcategoryAlreadyExistsException {
        Long categoryId = 1L;
        String subcategoryName = "Museum";
        given(categoryRepository.findById(categoryId)).willReturn(Optional.empty());

        AddSubcategoryUseCase.AddSubcategoryCommand command = new AddSubcategoryUseCase.AddSubcategoryCommand(
                categoryId, subcategoryName);

        subcategoryService.addSubcategory(command);
    }

    @Test(expected = ConstraintViolationException.class)
    public void whenCategoryIdIsInvalidThenCreateAddSubcategoryCommandWillFail() {
        Long categoryId = -1L;
        String subcategoryName = "Museum";
        new AddSubcategoryUseCase.AddSubcategoryCommand(
                categoryId, subcategoryName);
    }

    @Test(expected = ConstraintViolationException.class)
    public void whenSubcategoryNameIsEmptyThenCreateAddSubcategoryCommandWillFail() {
        Long categoryId = 1L;
        String subcategoryName = "";
        new AddSubcategoryUseCase.AddSubcategoryCommand(
                categoryId, subcategoryName);
    }

    @Test
    public void whenAddSubcategoryCommandIsValidThenAddSubcategoryWillSucceed() throws
            CategoryNotFoundException, SubcategoryAlreadyExistsException {
        Long categoryId = 1L;
        String subcategoryName = "Museum";
        Category category = Category.withId(categoryId, "Institution");

        given(categoryRepository.findById(categoryId)).willReturn(Optional.of(category));

        AddSubcategoryUseCase.AddSubcategoryCommand command = new AddSubcategoryUseCase.AddSubcategoryCommand(
                categoryId, subcategoryName);
        subcategoryService.addSubcategory(command);

        Mockito.verify(subcategoryRepository, times(1)).save(notNull());
    }

    @Test(expected = CategoryNotFoundException.class)
    public void whenCategoryDoesntExistThenGetSubcategoriesWillFail() throws CategoryNotFoundException {
        Long categoryId = 1L;
        Integer page = 0;
        Integer size = 3;
        given(categoryRepository.findById(categoryId)).willReturn(Optional.empty());

        subcategoryService.getSubcategories(categoryId, page, size);
    }

    @Test(expected = ConstraintViolationException.class)
    public void whenCategoryIdIsInvalidThenGetSubcategoriesWillFail() throws CategoryNotFoundException {
        Long categoryId = -1L;
        Integer page = 0;
        Integer size = 3;
        subcategoryService.getSubcategories(categoryId, page, size);
    }
}
