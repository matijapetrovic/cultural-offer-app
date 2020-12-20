package cultureapp.rest;

import cultureapp.rest.cultural_offer.CulturalOfferControllerIntegrationTest;
import cultureapp.rest.subcategory.SubcategoryControllerIntegrationTest;
import cultureapp.rest.subscription.SubscriptionControllerIntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SubcategoryControllerIntegrationTest.class,
        CulturalOfferControllerIntegrationTest.class,
        SubscriptionControllerIntegrationTest.class
})
public class CultureAppControllerIntegrationTests {
}
