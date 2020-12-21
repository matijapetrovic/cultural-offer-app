package cultureapp.domain;

import cultureapp.domain.review.ReviewRepositoryIntegrationTest;
import cultureapp.domain.category.CategoryRepositoryIntegrationTest;
import cultureapp.domain.subcategory.SubcategoryRepositoryIntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SubcategoryRepositoryIntegrationTest.class,
        ReviewRepositoryIntegrationTest.class,
        CategoryRepositoryIntegrationTest.class
})
public class CultureAppRepositoryIntegrationTests {
}
