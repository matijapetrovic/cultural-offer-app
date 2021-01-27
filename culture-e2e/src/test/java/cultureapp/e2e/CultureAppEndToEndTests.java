package cultureapp.e2e;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ActivateAccountTest.class,
        DashboardTest.class,
        CategoryTest.class,
        CulturalOfferTest.class,
        LoginTest.class,
        MapTest.class,
        RegisterTest.class,
        CulturalOfferTableTest.class,
        NewsTest.class,
        SubcategoryTest.class
})
public class CultureAppEndToEndTests {
}
