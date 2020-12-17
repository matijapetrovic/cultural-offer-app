package cultureapp.domain.subcategory;

import static cultureapp.common.CategoryTestData.*;
import static cultureapp.common.SubcategoryTestData.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class SubcategoryRepositoryIntegrationTest {
    private final int PAGE_SIZE = 2;
    private final Pageable FIRST_PAGE = PageRequest.of(0, PAGE_SIZE);

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    @Test
    public void whenIdAndCategoryIdAreValidAndNotArchivedThenFindShouldSucceed() {
        Optional<Subcategory> resultOptional = subcategoryRepository.findByIdAndCategoryIdAndArchivedFalse(
                EXISTING_CATEGORY_ID,
                EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1
        );
        assertFalse(resultOptional.isEmpty());

        Subcategory result = resultOptional.get();
        assertFalse(result.isArchived());
        assertEquals(EXISTING_SUBCATEGORY_NAME_FOR_CATEGORY_ID_1, result.getName());
        assertEquals(EXISTING_CATEGORY_NAME, result.getCategory().getName());
    }

    @Test
    public void whenArchivedThenFindShouldReturnEmpty() {
        Optional<Subcategory> resultOptional = subcategoryRepository.findByIdAndCategoryIdAndArchivedFalse(
                EXISTING_CATEGORY_ID,
                NON_EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1
        );
        assertTrue(resultOptional.isEmpty());
    }

    @Test
    public void whenCategoryIdIsValidAndPageIsFirstThenFindShouldReturnNonEmpty() {
        Slice<Subcategory> result = subcategoryRepository.findAllByCategoryIdAndArchivedFalse(
                EXISTING_CATEGORY_ID,
                FIRST_PAGE
        );
        assertEquals(PAGE_SIZE, result.getNumberOfElements());
    }

    @Test
    public void whenCategoryIdIsInvalidAndPageIsFirstThenFindShouldReturnEmpty() {
        Slice<Subcategory> result = subcategoryRepository.findAllByCategoryIdAndArchivedFalse(
                NON_EXISTING_CATEGORY_ID,
                FIRST_PAGE
        );
        assertEquals(0, result.getNumberOfElements());
    }

    @Test
    public void whenCategoryIdIsValidAndPageIsFourthThenFindShouldReturnEmpty() {
        Slice<Subcategory> result = subcategoryRepository.findAllByCategoryIdAndArchivedFalse(
                EXISTING_CATEGORY_ID,
                PageRequest.of(3, PAGE_SIZE)
        );
        assertEquals(0, result.getNumberOfElements());
    }

}
