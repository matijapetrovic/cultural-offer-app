package cultureapp.rest;

import cultureapp.rest.review.ReviewControllerIntegrationTest;
import cultureapp.rest.subcategory.SubcategoryControllerIntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SubcategoryControllerIntegrationTest.class,
        ReviewControllerIntegrationTest.class
})
public class CultureAppControllerIntegrationTests {
}
