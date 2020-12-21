package cultureapp.domain;

import cultureapp.domain.image.ImageServiceUnitTest;
import cultureapp.domain.review.ReviewServiceUnitTest;
import cultureapp.domain.cultural_offer.service.GetSubscriptionsForUserServiceUnitTest;
import cultureapp.domain.cultural_offer.service.SubscribeToCulturalOfferNewsServiceUnitTest;
import cultureapp.domain.cultural_offer.service.UnsubscribeFromCulturalOfferNewsServiceUnitTest;
import cultureapp.domain.category.CategoryServiceUnitTest;
import cultureapp.domain.subcategory.SubcategoryServiceUnitTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SubcategoryServiceUnitTest.class,
        ReviewServiceUnitTest.class,
        SubscribeToCulturalOfferNewsServiceUnitTest.class,
        UnsubscribeFromCulturalOfferNewsServiceUnitTest.class,
        GetSubscriptionsForUserServiceUnitTest.class,
        CategoryServiceUnitTest.class,
        ImageServiceUnitTest.class
})
public class CultureAppDomainUnitTests {
}
