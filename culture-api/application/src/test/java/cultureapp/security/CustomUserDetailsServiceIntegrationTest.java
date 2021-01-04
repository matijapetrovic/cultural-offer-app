package cultureapp.security;

import static cultureapp.common.AuthenticationTestData.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CustomUserDetailsServiceIntegrationTest {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Test
    public void givenUserNameExistsThenLoadByUserNameWillReturnNonEmpty() {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(EXISTING_ADMINISTRATOR_EMAIL);

        assertNotNull(userDetails);
        assertEquals(EXISTING_ADMINISTRATOR_EMAIL, userDetails.getUsername());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void givenUserNameDoesntExistThenLoadByUserNameWillFail() {
        customUserDetailsService.loadUserByUsername(NON_EXISTING_ADMINISTRATOR_EMAIL);
    }
}
