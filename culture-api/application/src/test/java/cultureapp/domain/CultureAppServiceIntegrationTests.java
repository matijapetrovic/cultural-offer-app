package cultureapp.domain;

import cultureapp.domain.authentication.LoginServiceIntegrationTest;
import cultureapp.domain.cultural_offer.service.*;
import cultureapp.domain.review.ReviewServiceIntegrationTest;
import cultureapp.domain.category.CategoryServiceIntegrationTest;
import cultureapp.domain.subcategory.SubcategoryServiceIntegrationTest;
import cultureapp.domain.user.RegularUserServiceIntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SubcategoryServiceIntegrationTest.class,
        ReviewServiceIntegrationTest.class,
        GetSubscriptionsForUserServiceIntegrationTest.class,
        SubscribeToCulturalOfferNewsServiceIntegrationTest.class,
        UnsubscribeFromCulturalOfferNewsServiceIntegrationTest.class,
        CategoryServiceIntegrationTest.class,
        LoginServiceIntegrationTest.class,
        RegularUserServiceIntegrationTest.class,
        GetCulturalOfferLocationsServiceIntegrationTest.class
})
public class CultureAppServiceIntegrationTests {
}
