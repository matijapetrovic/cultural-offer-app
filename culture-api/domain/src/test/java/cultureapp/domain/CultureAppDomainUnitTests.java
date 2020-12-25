package cultureapp.domain;

import cultureapp.domain.cultural_offer.service.*;
import cultureapp.domain.cultural_offer.service.GetCulturalOfferLocationsServiceUnitTest;
import cultureapp.domain.cultural_offer.service.GetSubscriptionsForUserServiceUnitTest;
import cultureapp.domain.image.ImageServiceUnitTest;
import cultureapp.domain.review.ReviewServiceUnitTest;
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
        ImageServiceUnitTest.class,
        GetCulturalOfferLocationsServiceUnitTest.class,
        AddCulturalOfferServiceUnitTest.class,
        DeleteCulturalOfferServiceUnitTest.class,
        UpdateCulturalOfferServiceUnitTest.class,
        GetCulturalOffersQueryHandlerUnitTest.class,
        GetCulturalOffersQueryHandlerUnitTest.class
})
public class CultureAppDomainUnitTests {
}
