package cultureapp.domain.subcategory;

import static cultureapp.domain.subcategory.SubcategoryConstants.*;
import static org.junit.Assert.*;

import cultureapp.domain.category.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;


@RunWith(SpringRunner.class)
@DataJpaTest
public class SubcategoryRepositoryIntegrationTest {
    private static Long VALID_CATEGORY_ID;
    private static Long VALID_SUBCATEGORY_ID;
    private static Long ARCHIVED_SUBCATEGORY_ID;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    @Before
    public void setUp() {
        Category category = Category.of(CATEGORY_NAME);
        category = entityManager.persist(category);
        VALID_CATEGORY_ID = category.getId();

        Subcategory validSubcategory = Subcategory.of(category, SUBCATEGORY_NAME);
        validSubcategory = entityManager.persist(validSubcategory);
        VALID_SUBCATEGORY_ID = validSubcategory.getId();

        Subcategory archivedSubcategory = Subcategory.of(category, ARCHIVED_SUBCATEGORY_NAME);
        archivedSubcategory.archive();
        archivedSubcategory = entityManager.persist(archivedSubcategory);
        ARCHIVED_SUBCATEGORY_ID = archivedSubcategory.getId();
    }

    @Test
    public void whenIdAndCategoryIdAreValidAndNotArchivedThenFindShouldSucceed() {
        Optional<Subcategory> resultOptional = subcategoryRepository.findByIdAndCategoryIdAndArchivedFalse(
                VALID_CATEGORY_ID,
                VALID_SUBCATEGORY_ID
        );
        assertFalse(resultOptional.isEmpty());

        Subcategory result = resultOptional.get();
        assertFalse(result.isArchived());
        assertEquals(SUBCATEGORY_NAME, result.getName());
        assertEquals(CATEGORY_NAME, result.getCategory().getName());
    }

    @Test
    public void whenArchivedThenFindShouldReturnEmpty() {
        Optional<Subcategory> resultOptional = subcategoryRepository.findByIdAndCategoryIdAndArchivedFalse(
                VALID_CATEGORY_ID,
                ARCHIVED_SUBCATEGORY_ID
        );
        assertTrue(resultOptional.isEmpty());
    }

}
