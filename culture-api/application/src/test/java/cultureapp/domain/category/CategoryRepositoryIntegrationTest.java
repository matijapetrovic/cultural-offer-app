package cultureapp.domain.category;

import static cultureapp.common.CategoryTestData.*;
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
public class CategoryRepositoryIntegrationTest {
    private final int PAGE_SIZE = 2;
    private final Pageable FIRST_PAGE = PageRequest.of(0, PAGE_SIZE);

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void whenIdAndArchivedFalseAreValidThenShouldSucceed() {
        Optional<Category> resultOptional = categoryRepository.findByIdAndArchivedFalse(EXISTING_CATEGORY_ID);

        assertFalse(resultOptional.isEmpty());

        Category result = resultOptional.get();
        assertFalse(result.isArchived());
        assertEquals(result.getId(), EXISTING_CATEGORY_ID);
    }

    @Test
    public void whenArchivedThenShouldReturnEmpty() {
        Optional<Category> resultOptional = categoryRepository.findByIdAndArchivedFalse(NON_EXISTING_CATEGORY_ID);

        assertTrue(resultOptional.isEmpty());
    }

    @Test
    public void whenPageIsFirstThenFindShouldReturnNonEmpty() {
        Slice<Category> result = categoryRepository.findAllByArchivedFalse(FIRST_PAGE);

        assertFalse(result.isEmpty());
    }

    @Test
    public void whenPageIsOneHundredThenShouldReturnEmpty() {
        Slice<Category> result = categoryRepository.findAllByArchivedFalse(
                PageRequest.of(99, PAGE_SIZE));

        assertTrue(result.isEmpty());
    }

    @Test
    public void whenCategoryWithValidIdAndSubcategoryExistsThenShouldReturnNonEmpty() {
        boolean result = categoryRepository.existsWithSubcategory(EXISTING_CATEGORY_ID);

        assertTrue(result);
    }

    @Test
    public void whenCategoryWithValidIdAndSubcategoryDoesNotExistsThenShouldReturnEmpty() {
        boolean result = categoryRepository.existsWithSubcategory(21L);

        assertFalse(result);
    }
}
