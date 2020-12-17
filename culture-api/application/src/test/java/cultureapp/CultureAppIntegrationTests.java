package cultureapp;


import cultureapp.domain.CultureAppRepositoryIntegrationTests;
import cultureapp.domain.CultureAppServiceIntegrationTests;
import cultureapp.rest.CultureAppControllerIntegrationTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CultureAppRepositoryIntegrationTests.class,
        CultureAppServiceIntegrationTests.class,
        CultureAppControllerIntegrationTests.class
})
public class CultureAppIntegrationTests {
}
