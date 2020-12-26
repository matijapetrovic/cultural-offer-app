package cultureapp.domain;

import cultureapp.domain.cultural_offer.service.AddCulturalOfferServiceIntegrationTest;
import cultureapp.domain.cultural_offer.service.CulturalOfferRepositoryIntegrationTest;
import cultureapp.domain.review.ReviewRepositoryIntegrationTest;
import cultureapp.domain.category.CategoryRepositoryIntegrationTest;
import cultureapp.domain.subcategory.SubcategoryRepositoryIntegrationTest;
import cultureapp.domain.user.RegularUserRepository;
import cultureapp.domain.user.RegularUserRepositoryIntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SubcategoryRepositoryIntegrationTest.class,
        ReviewRepositoryIntegrationTest.class,
        CategoryRepositoryIntegrationTest.class,
        RegularUserRepositoryIntegrationTest.class,
        CulturalOfferRepositoryIntegrationTest.class
})
public class CultureAppRepositoryIntegrationTests {
}
