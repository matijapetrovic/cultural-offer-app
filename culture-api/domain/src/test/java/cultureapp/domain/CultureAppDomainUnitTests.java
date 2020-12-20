package cultureapp.domain;

import cultureapp.domain.category.CategoryServiceUnitTest;
import cultureapp.domain.subcategory.SubcategoryServiceUnitTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SubcategoryServiceUnitTest.class,
        CategoryServiceUnitTest.class
})
public class CultureAppDomainUnitTests {
}
