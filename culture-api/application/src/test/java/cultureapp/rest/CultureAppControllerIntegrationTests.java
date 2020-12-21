package cultureapp.rest;

import cultureapp.rest.review.ReviewControllerIntegrationTest;
import cultureapp.rest.cultural_offer.CulturalOfferControllerIntegrationTest;
import cultureapp.rest.category.CategoryControllerIntegrationTest;
import cultureapp.rest.subcategory.SubcategoryControllerIntegrationTest;
import cultureapp.rest.subscription.SubscriptionControllerIntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SubcategoryControllerIntegrationTest.class,
        ReviewControllerIntegrationTest.class,
        CulturalOfferControllerIntegrationTest.class,
        SubscriptionControllerIntegrationTest.class,
        CategoryControllerIntegrationTest.class
})
public class CultureAppControllerIntegrationTests {
}
