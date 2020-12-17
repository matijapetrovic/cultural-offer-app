package cultureapp.domain;

import cultureapp.domain.subcategory.SubcategoryServiceIntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SubcategoryServiceIntegrationTest.class
})
public class CultureAppServiceIntegrationTests {
}
