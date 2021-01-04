package cultureapp.security;

import static cultureapp.common.AuthenticationTestData.*;
import static org.junit.Assert.*;

import cultureapp.domain.account.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class JWTAuthenticationServiceIntegrationTest {

    @Autowired
    private JWTAuthenticationService jwtAuthenticationService;

    @Autowired
    private AuthenticationManager authenticationManager;

    /*
    -----------------------
    AUTHENTICATE
    -----------------------
    */

    @Test
    public void givenEmailAndPasswordExistThenAuthenticationWillSucceed() {
        Account account = jwtAuthenticationService.authenticate(EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);

        assertNotNull(account);
        assertEquals(EXISTING_ADMINISTRATOR_EMAIL, account.getEmail());
    }

    @Test(expected = BadCredentialsException.class)
    public void givenEmailAndPasswordDoesntExistThenAuthenticationWillFail() {
        jwtAuthenticationService.authenticate(NON_EXISTING_ADMINISTRATOR_EMAIL, NON_EXISTING_ADMINISTRATOR_PASSWORD);
    }

    /*
    -----------------------
    REAUTHENTICATE
    -----------------------
    */

    @Test
    public void givenEmailAndPasswordExistThenReauthenticateWillSucceed() {
        boolean reauthenticate = jwtAuthenticationService.reauthenticate(EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);

        assertTrue(reauthenticate);
    }

    @Test(expected = BadCredentialsException.class)
    public void givenEmailAndPasswordDoesntExistThenReauthenticateWillFail() {
        jwtAuthenticationService.authenticate(NON_EXISTING_ADMINISTRATOR_EMAIL, NON_EXISTING_ADMINISTRATOR_PASSWORD);
    }

    /*
    -----------------------
    GET AUTHENTICATED
    -----------------------
    */

    @Test
    public void givenAuthenticatedUserExistsThenGetAuthenticatedWillReturnNonEmpty() {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Account account = jwtAuthenticationService.getAuthenticated();
        assertNotNull(account);
        assertEquals(EXISTING_ADMINISTRATOR_EMAIL, account.getEmail());
    }

    @Test(expected = NullPointerException.class)
    public void givenAuthenticatedUserDoesntExistsThenGetAuthenticatedWillReturnEmpty() {
        Account account = jwtAuthenticationService.getAuthenticated();
    }
}
