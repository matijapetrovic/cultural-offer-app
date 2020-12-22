package cultureapp.domain.subcategory;

import static cultureapp.common.SubcategoryTestData.*;
import static cultureapp.common.CategoryTestData.*;
import static org.junit.Assert.*;

import cultureapp.domain.category.exception.CategoryNotFoundException;
import cultureapp.domain.subcategory.command.AddSubcategoryUseCase;
import cultureapp.domain.subcategory.command.UpdateSubcategoryUseCase;
import cultureapp.domain.subcategory.exception.SubcategoryAlreadyExistsException;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import cultureapp.domain.subcategory.query.GetSubcategoriesQueryHandler;
import cultureapp.domain.subcategory.query.GetSubcategoryByIdQueryHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class SubcategoryServiceIntegrationTest {

    @Autowired
    private SubcategoryService subcategoryService;

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    @Test
    public void givenAddSubcategoryCommandIsValidThenAddSubcategoryWillSucceed() throws
            CategoryNotFoundException, SubcategoryAlreadyExistsException {
        long subcategoryCount = subcategoryRepository.count();

        AddSubcategoryUseCase.AddSubcategoryCommand command = new AddSubcategoryUseCase.AddSubcategoryCommand(
                EXISTING_CATEGORY_ID,
                NON_EXISTING_SUBCATEGORY_NAME_FOR_CATEGORY_ID_1);
        subcategoryService.addSubcategory(command);

        assertEquals(subcategoryCount + 1, subcategoryRepository.count());

        Subcategory subcategory = subcategoryRepository.findAll().get((int) subcategoryCount);
        assertEquals(NON_EXISTING_SUBCATEGORY_NAME_FOR_CATEGORY_ID_1, subcategory.getName());
        assertEquals(EXISTING_CATEGORY_ID, subcategory.getCategory().getId());
        subcategoryRepository.delete(subcategory);
    }

    @Test(expected = CategoryNotFoundException.class)
    public void givenCategoryDoesntExistThenAddSubcategoryWillFail()
            throws CategoryNotFoundException, SubcategoryAlreadyExistsException {
        AddSubcategoryUseCase.AddSubcategoryCommand command = new AddSubcategoryUseCase.AddSubcategoryCommand(
                NON_EXISTING_CATEGORY_ID,
                NON_EXISTING_SUBCATEGORY_NAME_FOR_CATEGORY_ID_1);

        subcategoryService.addSubcategory(command);
    }

    @Test(expected = SubcategoryAlreadyExistsException.class)
    public void givenSubcategoryNameAlreadyExistsThenAddSubcategoryWillFail() throws
            CategoryNotFoundException, SubcategoryAlreadyExistsException {

        AddSubcategoryUseCase.AddSubcategoryCommand command = new AddSubcategoryUseCase.AddSubcategoryCommand(
                EXISTING_CATEGORY_ID,
                EXISTING_SUBCATEGORY_NAME_FOR_CATEGORY_ID_1);
        subcategoryService.addSubcategory(command);
    }

    // getAll()

    @Test
    public void givenCategoryIdIsValidAndPageIsFirstThenGetSubcategoriesWillReturnNonEmpty() throws CategoryNotFoundException {
        GetSubcategoriesQueryHandler.GetSubcategoriesQuery query =
                new GetSubcategoriesQueryHandler.GetSubcategoriesQuery(
                        EXISTING_CATEGORY_ID,
                        FIRST_PAGE_FOR_CATEGORY_ID_1,
                        SUBCATEGORY_PAGE_SIZE);

        Slice<GetSubcategoriesQueryHandler.GetSubcategoriesDTO> result =
                subcategoryService.handleGetSubcategories(query);

        assertEquals(FIRST_PAGE_NUM_SUBCATEGORIES_FOR_CATEGORY_ID_1, result.getNumberOfElements());
        assertTrue(result.hasNext());
        assertFalse(result.hasPrevious());
    }

    @Test
    public void givenCategoryIdIsValidAndPageIsLastThenGetSubcategoriesWillReturnNonEmpty() throws CategoryNotFoundException {
        GetSubcategoriesQueryHandler.GetSubcategoriesQuery query =
                new GetSubcategoriesQueryHandler.GetSubcategoriesQuery(
                        EXISTING_CATEGORY_ID,
                        LAST_PAGE_FOR_CATEGORY_ID_1,
                        SUBCATEGORY_PAGE_SIZE);

        Slice<GetSubcategoriesQueryHandler.GetSubcategoriesDTO> result =
                subcategoryService.handleGetSubcategories(query);

        assertEquals(LAST_PAGE_NUM_SUBCATEGORIES_FOR_CATEGORY_ID_1, result.getNumberOfElements());
        assertFalse(result.hasNext());
        assertTrue(result.hasPrevious());
    }

    @Test(expected = CategoryNotFoundException.class)
    public void givenCategoryDoesntExistThenGetSubcategoriesWillFail() throws CategoryNotFoundException {
        GetSubcategoriesQueryHandler.GetSubcategoriesQuery query =
                new GetSubcategoriesQueryHandler.GetSubcategoriesQuery(
                        NON_EXISTING_CATEGORY_ID,
                        FIRST_PAGE_FOR_CATEGORY_ID_1,
                        SUBCATEGORY_PAGE_SIZE);

        subcategoryService.handleGetSubcategories(query);
    }


    // getSingle()

    @Test
    public void givenSubcategoryExistsThenGetSubcategoryWillSucceed() throws SubcategoryNotFoundException {
        GetSubcategoryByIdQueryHandler.GetSubcategoryByIdQuery query =
                new GetSubcategoryByIdQueryHandler.GetSubcategoryByIdQuery(
                        EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1,
                        EXISTING_CATEGORY_ID);

        GetSubcategoryByIdQueryHandler.GetSubcategoryByIdDTO result =
                subcategoryService.handleGetSubcategory(query);

        assertNotNull(result);
        assertEquals(EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1, result.getId());
        assertEquals(EXISTING_SUBCATEGORY_NAME_FOR_CATEGORY_ID_1, result.getName());
        assertEquals(EXISTING_CATEGORY_ID, result.getCategoryId());
    }

    @Test(expected = SubcategoryNotFoundException.class)
    public void givenSubcategoryDoesntExistThenGetSubcategoryWillFail() throws SubcategoryNotFoundException {
        GetSubcategoryByIdQueryHandler.GetSubcategoryByIdQuery query =
                new GetSubcategoryByIdQueryHandler.GetSubcategoryByIdQuery(
                        NON_EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1,
                        EXISTING_CATEGORY_ID);

        subcategoryService.handleGetSubcategory(query);
    }

    @Test(expected = SubcategoryNotFoundException.class)
    public void givenCategoryDoesntExistThenGetSubcategoryWillFail() throws SubcategoryNotFoundException {
        GetSubcategoryByIdQueryHandler.GetSubcategoryByIdQuery query =
                new GetSubcategoryByIdQueryHandler.GetSubcategoryByIdQuery(
                        EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1,
                        NON_EXISTING_CATEGORY_ID);

        subcategoryService.handleGetSubcategory(query);
    }

    // update

    @Test
    public void givenValidSubcategoryCommandThenUpdateSubcategoryWillSucceed()
            throws SubcategoryAlreadyExistsException, SubcategoryNotFoundException {
        UpdateSubcategoryUseCase.UpdateSubcategoryCommand command =
                new UpdateSubcategoryUseCase.UpdateSubcategoryCommand(
                        EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1,
                        EXISTING_CATEGORY_ID,
                        NON_EXISTING_SUBCATEGORY_NAME_FOR_CATEGORY_ID_1);

        subcategoryService.updateSubcategory(command);

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

    @Test(expected = SubcategoryNotFoundException.class)
    public void givenSubcategoryDoesntExistThenUpdateSubcategoryWillFail() throws SubcategoryNotFoundException, SubcategoryAlreadyExistsException {
        UpdateSubcategoryUseCase.UpdateSubcategoryCommand command =
                new UpdateSubcategoryUseCase.UpdateSubcategoryCommand(
                        NON_EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1,
                        EXISTING_CATEGORY_ID,
                        NON_EXISTING_SUBCATEGORY_NAME_FOR_CATEGORY_ID_1);
        subcategoryService.updateSubcategory(command);
    }

    @Test(expected = SubcategoryAlreadyExistsException.class)
    public void givenSubcategoryNameAlreadyExistsThenUpdateSubcategoryWillFail()
            throws SubcategoryAlreadyExistsException, SubcategoryNotFoundException {
    UpdateSubcategoryUseCase.UpdateSubcategoryCommand command =
            new UpdateSubcategoryUseCase.UpdateSubcategoryCommand(
                    EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1,
                    EXISTING_CATEGORY_ID,
                    EXISTING_SUBCATEGORY_NAME_2_FOR_CATEGORY_ID_1);
        subcategoryService.updateSubcategory(command);
    }

    // delete

    @Test(expected = SubcategoryNotFoundException.class)
    public void givenSubcategoryDoesntExistThenDeleteSubcategoryWillFail() throws SubcategoryNotFoundException {
        subcategoryService.deleteSubcategoryById(NON_EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1, EXISTING_CATEGORY_ID);
    }

    @Test
    public void givenValidSubcategoryCommandThenDeleteSubcategoryWillSucceed()
            throws SubcategoryNotFoundException {
        subcategoryService.deleteSubcategoryById(EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1, EXISTING_CATEGORY_ID);

        Optional<Subcategory> subcategoryOptional =
                subcategoryRepository.findByIdAndCategoryIdAndArchivedFalse(
                        EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1, EXISTING_CATEGORY_ID);
        assertTrue(subcategoryOptional.isEmpty());

        Subcategory subcategory = subcategoryRepository.findById(SubcategoryId.of(
                EXISTING_CATEGORY_ID, EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1)).orElseThrow();
        subcategory.unarchive();
        subcategoryRepository.save(subcategory);
    }

}
