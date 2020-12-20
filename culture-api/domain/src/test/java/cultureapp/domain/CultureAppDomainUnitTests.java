package cultureapp.domain;

import cultureapp.domain.review.ReviewServiceUnitTest;
import cultureapp.domain.subcategory.SubcategoryServiceUnitTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SubcategoryServiceUnitTest.class,
        ReviewServiceUnitTest.class
})
public class CultureAppDomainUnitTests {
}
