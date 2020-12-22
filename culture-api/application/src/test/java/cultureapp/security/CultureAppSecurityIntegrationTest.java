package cultureapp.security;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CustomUserDetailsServiceIntegrationTest.class,
        JWTAuthenticationServiceIntegrationTest.class,
})
public class CultureAppSecurityIntegrationTest {
}
