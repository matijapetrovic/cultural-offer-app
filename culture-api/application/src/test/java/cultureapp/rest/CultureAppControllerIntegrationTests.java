package cultureapp.rest;

import cultureapp.rest.category.CategoryControllerIntegrationTest;
import cultureapp.rest.subcategory.SubcategoryControllerIntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SubcategoryControllerIntegrationTest.class,
        CategoryControllerIntegrationTest.class
})
public class CultureAppControllerIntegrationTests {
}
