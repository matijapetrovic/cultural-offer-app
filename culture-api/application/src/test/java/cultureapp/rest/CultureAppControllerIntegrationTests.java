package cultureapp.rest;

import cultureapp.rest.subcategory.SubcategoryControllerIntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SubcategoryControllerIntegrationTest.class
})
public class CultureAppControllerIntegrationTests {
}
