package cultureapp.e2e;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CategoryTest.class,
        CulturalOfferTest.class,
        LoginTest.class,
        MapTest.class,
        RegisterTest.class
})
public class CultureAppEndToEndTests {
}
