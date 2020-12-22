package cultureapp.domain.authentication;

import static cultureapp.common.AuthenticationTestData.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import cultureapp.domain.account.Account;
import cultureapp.domain.authentication.command.LoginUseCase;
import cultureapp.domain.authentication.exception.AccountNotActivatedException;
import cultureapp.security.CustomUserDetails;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class LoginServiceIntegrationTest {

    @Autowired
    private LoginService loginService;

    @Test
    public void givenLoginCommandIsValidThenUserLoginWillSucceed() throws AccountNotActivatedException {
        LoginUseCase.LoginCommand command =
                new LoginUseCase.LoginCommand(EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        loginService.login(command);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        Account account =  principal.getAccount();

        assertNotNull(account);
        assertEquals(account.getEmail(), EXISTING_ADMINISTRATOR_EMAIL);
    }

    @Test(expected = AccountNotActivatedException.class)
    public void givenLoginCommandIsValidAndAccountNotActivatedThenUserLoginWillFail() throws AccountNotActivatedException {
        LoginUseCase.LoginCommand command =
                new LoginUseCase.LoginCommand(NOT_ACTIVATED_ACCOUNT_EXISTING_EMAIL, NOT_ACTIVATED_ACCOUNT_EXISTING_PASSWORD);
        loginService.login(command);
    }

    @Test(expected = ConstraintViolationException.class)
    public void givenLoginCommandIsInvalidThenUserLoginWillFail() throws AccountNotActivatedException {
        LoginUseCase.LoginCommand command =
                new LoginUseCase.LoginCommand(NON_VALID_ACCOUNT_EMAIL, NON_VALID_ACCOUNT_PASSWORD);
        loginService.login(command);
    }

    @Test(expected = BadCredentialsException.class)
    public void givenLoginCommandIsValidAndAccountDoesntExistsThenLoginWillFail() throws AccountNotActivatedException {
            LoginUseCase.LoginCommand command =
                    new LoginUseCase.LoginCommand(NON_EXISTING_ADMINISTRATOR_EMAIL, NON_EXISTING_ADMINISTRATOR_PASSWORD);
            loginService.login(command);
    }
}
