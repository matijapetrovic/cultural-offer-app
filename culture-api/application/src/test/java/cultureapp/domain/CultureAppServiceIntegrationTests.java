package cultureapp.domain;

import cultureapp.domain.authentication.LoginServiceIntegrationTest;
import cultureapp.domain.cultural_offer.service.*;
import cultureapp.domain.news.NewsServiceIntegrationTest;
import cultureapp.domain.reply.ReplyServiceIntegrationTest;
import cultureapp.domain.review.ReviewServiceIntegrationTest;
import cultureapp.domain.category.CategoryServiceIntegrationTest;
import cultureapp.domain.subcategory.SubcategoryServiceIntegrationTest;
import cultureapp.domain.user.RegularUserServiceIntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ReplyServiceIntegrationTest.class,
        SubcategoryServiceIntegrationTest.class,
        ReviewServiceIntegrationTest.class,
        GetSubscriptionsForUserServiceIntegrationTest.class,
        SubscribeToCulturalOfferNewsServiceIntegrationTest.class,
        UnsubscribeFromCulturalOfferNewsServiceIntegrationTest.class,
        CategoryServiceIntegrationTest.class,
        LoginServiceIntegrationTest.class,
        RegularUserServiceIntegrationTest.class,
        GetCulturalOfferLocationsServiceIntegrationTest.class,
        AddCulturalOfferServiceIntegrationTest.class,
        UpdateCulturalOfferServiceIntegrationTest.class,
        DeleteCulturalOffersServiceIntegrationTest.class,
        GetCulturalOffersServiceIntegrationTest.class,
        GetCulturalOfferByIdServiceIntegrationTest.class,
        NewsServiceIntegrationTest.class
})
public class CultureAppServiceIntegrationTests {
}
