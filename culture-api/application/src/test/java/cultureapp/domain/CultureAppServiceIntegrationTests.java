package cultureapp.domain;

import cultureapp.domain.cultural_offer.service.GetSubscriptionsForUserServiceIntegrationTest;
import cultureapp.domain.cultural_offer.service.SubscribeToCulturalOfferNewsServiceIntegrationTest;
import cultureapp.domain.cultural_offer.service.UnsubscribeFromCulturalOfferNewsServiceIntegrationTest;
import cultureapp.domain.subcategory.SubcategoryServiceIntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SubcategoryServiceIntegrationTest.class,
        GetSubscriptionsForUserServiceIntegrationTest.class,
        SubscribeToCulturalOfferNewsServiceIntegrationTest.class,
        UnsubscribeFromCulturalOfferNewsServiceIntegrationTest.class
})
public class CultureAppServiceIntegrationTests {
}
