package cultureapp.domain;

import cultureapp.domain.cultural_offer.service.GetSubscriptionsForUserServiceUnitTest;
import cultureapp.domain.cultural_offer.service.SubscribeToCulturalOfferNewsServiceUnitTest;
import cultureapp.domain.cultural_offer.service.UnsubscribeFromCulturalOfferNewsServiceUnitTest;
import cultureapp.domain.subcategory.SubcategoryServiceUnitTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SubcategoryServiceUnitTest.class,
        SubscribeToCulturalOfferNewsServiceUnitTest.class,
        UnsubscribeFromCulturalOfferNewsServiceUnitTest.class,
        GetSubscriptionsForUserServiceUnitTest.class
})
public class CultureAppDomainUnitTests {
}
