package cultureapp.domain;

import cultureapp.domain.subcategory.SubcategoryRepositoryIntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SubcategoryRepositoryIntegrationTest.class
})
public class CultureAppRepositoryIntegrationTests {
}
