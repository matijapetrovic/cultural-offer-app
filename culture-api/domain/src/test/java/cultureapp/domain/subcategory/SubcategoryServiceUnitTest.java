package cultureapp.domain.subcategory;

import static cultureapp.common.CategoryTestData.*;
import static cultureapp.common.SubcategoryTestData.*;

import cultureapp.domain.category.Category;
import cultureapp.domain.category.CategoryRepository;
import cultureapp.domain.category.exception.CategoryNotFoundException;
import cultureapp.domain.core.AuthenticationService;
import cultureapp.domain.subcategory.command.AddSubcategoryUseCase;
import cultureapp.domain.subcategory.command.UpdateSubcategoryUseCase;
import cultureapp.domain.subcategory.exception.SubcategoryAlreadyExistsException;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import cultureapp.domain.subcategory.query.GetSubcategoriesQueryHandler;
import cultureapp.domain.subcategory.query.GetSubcategoryByIdQueryHandler;
import cultureapp.domain.user.RegularUserRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.junit.Assert.*;

public class SubcategoryServiceUnitTest {

    private final SubcategoryRepository subcategoryRepository =
            Mockito.mock(SubcategoryRepository.class);

    private final CategoryRepository categoryRepository =
            Mockito.mock(CategoryRepository.class);


    private final AuthenticationService authenticationService =
            Mockito.mock(AuthenticationService.class);
    private final RegularUserRepository regularUserRepository =
            Mockito.mock(RegularUserRepository.class);

    private final SubcategoryService subcategoryService =
            new SubcategoryService(
                    subcategoryRepository,
                    categoryRepository,
                    authenticationService,
                    regularUserRepository
            );

    // addSubcategories()

    @Test(expected = CategoryNotFoundException.class)
    public void givenCategoryDoesntExistThenAddSubcategoryWillFail() throws CategoryNotFoundException, SubcategoryAlreadyExistsException {
        given(categoryRepository.findByIdAndArchivedFalse(VALID_CATEGORY_ID)).willReturn(Optional.empty());

        AddSubcategoryUseCase.AddSubcategoryCommand command = new AddSubcategoryUseCase.AddSubcategoryCommand(
                VALID_CATEGORY_ID, VALID_SUBCATEGORY_NAME);

        subcategoryService.addSubcategory(command);
    }

    @Test(expected = ConstraintViolationException.class)
    public void givenCategoryIdIsInvalidThenCreateAddSubcategoryCommandWillFail() {
        new AddSubcategoryUseCase.AddSubcategoryCommand(
                INVALID_CATEGORY_ID, VALID_SUBCATEGORY_NAME);
    }

    @Test(expected = ConstraintViolationException.class)
    public void givenSubcategoryNameIsEmptyThenCreateAddSubcategoryCommandWillFail() {
        new AddSubcategoryUseCase.AddSubcategoryCommand(
                VALID_CATEGORY_ID, INVALID_SUBCATEGORY_NAME);
    }

    @Test
    public void givenAddSubcategoryCommandIsValidThenAddSubcategoryWillSucceed() throws
            CategoryNotFoundException, SubcategoryAlreadyExistsException {
        Category category = Category.withId(VALID_CATEGORY_ID, VALID_CATEGORY_NAME);

        given(categoryRepository.findByIdAndArchivedFalse(VALID_CATEGORY_ID)).willReturn(Optional.of(category));

        AddSubcategoryUseCase.AddSubcategoryCommand command = new AddSubcategoryUseCase.AddSubcategoryCommand(
                VALID_CATEGORY_ID, VALID_CATEGORY_NAME);
        subcategoryService.addSubcategory(command);

        then(subcategoryRepository)
            .should()
            .save(notNull());
    }

    @Test(expected = SubcategoryAlreadyExistsException.class)
    public void givenSubcategoryNameAlreadyExistsThenAddSubcategoryWillFail() throws
            CategoryNotFoundException, SubcategoryAlreadyExistsException {
        Category category = Category.withId(VALID_CATEGORY_ID, VALID_CATEGORY_NAME);
        given(categoryRepository.findByIdAndArchivedFalse(VALID_CATEGORY_ID)).willReturn(Optional.of(category));

        given(subcategoryRepository.save(notNull())).willThrow(DataIntegrityViolationException.class);

        AddSubcategoryUseCase.AddSubcategoryCommand command = new AddSubcategoryUseCase.AddSubcategoryCommand(
                VALID_CATEGORY_ID, VALID_CATEGORY_NAME);
        subcategoryService.addSubcategory(command);
    }

    // getSubcategories()


    @Test(expected = CategoryNotFoundException.class)
    public void givenCategoryDoesntExistThenGetSubcategoriesWillFail() throws CategoryNotFoundException {
        given(categoryRepository.findByIdAndArchivedFalse(VALID_CATEGORY_ID)).willReturn(Optional.empty());

        GetSubcategoriesQueryHandler.GetSubcategoriesQuery query =
                new GetSubcategoriesQueryHandler.GetSubcategoriesQuery(VALID_CATEGORY_ID, 0, 2);

        subcategoryService.handleGetSubcategories(query);
    }

    @Test(expected = ConstraintViolationException.class)
    public void givenCategoryIdIsInvalidThenGetSubcategoriesWillFail() {
                new GetSubcategoriesQueryHandler.GetSubcategoriesQuery(INVALID_CATEGORY_ID, 0, 2);
    }

    @Test
    public void givenCategoryIdIsValidAndPageIsFirstThenGetSubcategoriesWillReturnNonEmpty() throws CategoryNotFoundException {
        Category category = Category.withId(VALID_CATEGORY_ID, VALID_CATEGORY_NAME);
        List<Subcategory> subcategories = List.of(
                Subcategory.of(category, VALID_CATEGORY_NAME),
                Subcategory.of(category, VALID_CATEGORY_NAME));
        Slice<Subcategory> slice = new SliceImpl<>(subcategories);

        given(categoryRepository.findByIdAndArchivedFalse(VALID_CATEGORY_ID)).willReturn(Optional.of(category));
        given(subcategoryRepository.findAllByCategoryIdAndArchivedFalse(notNull(), notNull())).willReturn(slice);

        GetSubcategoriesQueryHandler.GetSubcategoriesQuery query =
                new GetSubcategoriesQueryHandler.GetSubcategoriesQuery(VALID_CATEGORY_ID, 0, 2);

        Slice<GetSubcategoriesQueryHandler.GetSubcategoriesDTO> result =
                subcategoryService.handleGetSubcategories(query);

        then(subcategoryRepository)
                .should()
                .findAllByCategoryIdAndArchivedFalse(notNull(), notNull());

        assertEquals(slice.getNumberOfElements(), result.getNumberOfElements());
    }

    // getSubcategory()

    @Test(expected = SubcategoryNotFoundException.class)
    public void givenSubcategoryDoesntExistThenGetSubcategoryWillFail() throws SubcategoryNotFoundException {
        given(subcategoryRepository.findByIdAndCategoryIdAndArchivedFalse(VALID_SUBCATEGORY_ID, VALID_CATEGORY_ID))
                .willReturn(Optional.empty());

        GetSubcategoryByIdQueryHandler.GetSubcategoryByIdQuery query =
                new GetSubcategoryByIdQueryHandler.GetSubcategoryByIdQuery(VALID_SUBCATEGORY_ID, VALID_CATEGORY_ID);

        subcategoryService.handleGetSubcategory(query);
    }

    @Test
    public void givenSubcategoryExistsThenGetSubcategoryWillSucceed() throws SubcategoryNotFoundException {
        Category category = Category.withId(VALID_CATEGORY_ID, VALID_CATEGORY_NAME);
        Subcategory subcategory = Subcategory.withId(VALID_SUBCATEGORY_ID, category, VALID_CATEGORY_NAME);
        given(subcategoryRepository.findByIdAndCategoryIdAndArchivedFalse(VALID_SUBCATEGORY_ID, VALID_CATEGORY_ID))
                .willReturn(Optional.of(subcategory));

        GetSubcategoryByIdQueryHandler.GetSubcategoryByIdQuery query =
                new GetSubcategoryByIdQueryHandler.GetSubcategoryByIdQuery(VALID_SUBCATEGORY_ID, VALID_CATEGORY_ID);

        GetSubcategoryByIdQueryHandler.GetSubcategoryByIdDTO result =
                subcategoryService.handleGetSubcategory(query);

        assertNotNull(result);
        assertEquals(VALID_SUBCATEGORY_ID, result.getId());
        assertEquals(VALID_SUBCATEGORY_NAME, result.getName());
        assertEquals(VALID_CATEGORY_ID, result.getCategoryId());
    }

    // update

    @Test(expected = SubcategoryNotFoundException.class)
    public void givenSubcategoryDoesntExistThenUpdateSubcategoryWillFail() throws SubcategoryNotFoundException, SubcategoryAlreadyExistsException {
         given(subcategoryRepository.findByIdAndCategoryIdAndArchivedFalse(VALID_SUBCATEGORY_ID, VALID_CATEGORY_ID))
                .willReturn(Optional.empty());

        UpdateSubcategoryUseCase.UpdateSubcategoryCommand command =
                new UpdateSubcategoryUseCase.UpdateSubcategoryCommand(
                        VALID_SUBCATEGORY_ID,
                        VALID_CATEGORY_ID,
                        VALID_SUBCATEGORY_NAME);
        subcategoryService.updateSubcategory(command);
    }

    @Test(expected = ConstraintViolationException.class)
    public void givenSubcategoryNameIsInvalidThenCreatingUpdateCommandWillFail() {
            new UpdateSubcategoryUseCase.UpdateSubcategoryCommand(
                    VALID_SUBCATEGORY_ID,
                    VALID_CATEGORY_ID,
                    INVALID_SUBCATEGORY_NAME);
    }

    @Test(expected = SubcategoryAlreadyExistsException.class)
    public void givenSubcategoryNameAlreadyExistsThenUpdateSubcategoryWillFail()
            throws SubcategoryAlreadyExistsException, SubcategoryNotFoundException {
        Category category = Category.withId(VALID_CATEGORY_ID, VALID_CATEGORY_NAME);
        Subcategory subcategory = Subcategory.withId(VALID_SUBCATEGORY_ID, category, VALID_CATEGORY_NAME);
        given(subcategoryRepository.findByIdAndCategoryIdAndArchivedFalse(VALID_SUBCATEGORY_ID, VALID_CATEGORY_ID))
                .willReturn(Optional.of(subcategory));

        given(subcategoryRepository.save(notNull())).willThrow(DataIntegrityViolationException.class);

        UpdateSubcategoryUseCase.UpdateSubcategoryCommand command =
            new UpdateSubcategoryUseCase.UpdateSubcategoryCommand(
                    VALID_SUBCATEGORY_ID,
                    VALID_CATEGORY_ID,
                    VALID_UPDATE_SUBCATEGORY_NAME);

        subcategoryService.updateSubcategory(command);
    }

    @Test
    public void givenValidSubcategoryCommandThenUpdateSubcategoryWillSucceed()
            throws SubcategoryAlreadyExistsException, SubcategoryNotFoundException {
        Category category = Category.withId(VALID_CATEGORY_ID, VALID_CATEGORY_NAME);
        Subcategory subcategory = Subcategory.withId(VALID_SUBCATEGORY_ID, category, VALID_CATEGORY_NAME);
        given(subcategoryRepository.findByIdAndCategoryIdAndArchivedFalse(VALID_SUBCATEGORY_ID, VALID_CATEGORY_ID))
                .willReturn(Optional.of(subcategory));

        UpdateSubcategoryUseCase.UpdateSubcategoryCommand command =
                new UpdateSubcategoryUseCase.UpdateSubcategoryCommand(
                        VALID_SUBCATEGORY_ID,
                        VALID_CATEGORY_ID,
                        VALID_UPDATE_SUBCATEGORY_NAME);

        subcategoryService.updateSubcategory(command);
    }

    // deleteSubcategory()

    @Test(expected = SubcategoryNotFoundException.class)
    public void givenSubcategoryDoesntExistThenDeleteSubcategoryWillFail() throws SubcategoryNotFoundException, SubcategoryAlreadyExistsException {
        given(subcategoryRepository.findByIdAndCategoryIdAndArchivedFalse(VALID_SUBCATEGORY_ID, VALID_CATEGORY_ID))
                .willReturn(Optional.empty());

        subcategoryService.deleteSubcategoryById(VALID_SUBCATEGORY_ID, VALID_CATEGORY_ID);
    }

    @Test
    public void givenValidSubcategoryCommandThenDeleteSubcategoryWillSucceed()
            throws SubcategoryNotFoundException {
        Category category = Category.withId(VALID_CATEGORY_ID, VALID_CATEGORY_NAME);
        Subcategory subcategory = Subcategory.withId(VALID_SUBCATEGORY_ID, category, VALID_CATEGORY_NAME);
        given(subcategoryRepository.findByIdAndCategoryIdAndArchivedFalse(VALID_SUBCATEGORY_ID, VALID_CATEGORY_ID))
                .willReturn(Optional.of(subcategory));

        subcategoryService.deleteSubcategoryById(VALID_SUBCATEGORY_ID, VALID_CATEGORY_ID);
    }
}
