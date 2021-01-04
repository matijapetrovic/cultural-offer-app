package cultureapp.domain.user;

import static cultureapp.common.AuthenticationTestData.*;
import static cultureapp.common.RegularUserTestData.*;
import static org.junit.Assert.*;

import cultureapp.domain.account.exception.AccountAlreadyExistsException;
import cultureapp.domain.user.command.RegisterRegularUserUseCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class RegularUserServiceIntegrationTest {

    @Autowired
    private RegisterRegularUserUseCase registerRegularUserUseCase;

    @Autowired
    private RegularUserRepository regularUserRepository;

    @Test
    public void givenRegisterRegularUserCommandIsValidThenRegistrationWillSucceed() throws AccountAlreadyExistsException {
        long regularUserCount = regularUserRepository.count();

        RegisterRegularUserUseCase.RegisterRegularUserCommand command =
                new RegisterRegularUserUseCase.RegisterRegularUserCommand(
                        VALID_REGULAR_USER_FIRST_NAME,
                        VALID_REGULAR_USER_LAST_NAME,
                        NON_EXISTING_ADMINISTRATOR_EMAIL,
                        NON_EXISTING_ADMINISTRATOR_PASSWORD
                );
        registerRegularUserUseCase.addRegularUser(command);
        assertEquals(regularUserCount + 1, regularUserRepository.count());

        RegularUser regularUser = regularUserRepository.findAll().get((int) regularUserCount);
        assertNotNull(regularUser);
        assertEquals(VALID_REGULAR_USER_FIRST_NAME, regularUser.getFirstName());
        assertEquals(VALID_REGULAR_USER_LAST_NAME, regularUser.getLastName());
        assertEquals(NON_EXISTING_ADMINISTRATOR_EMAIL, regularUser.getEmail());
        regularUserRepository.delete(regularUser);
    }

    @Test(expected = ConstraintViolationException.class)
    public void givenRegisterRegularUserCommandIsInvalidThenRegistrationWillFail() throws AccountAlreadyExistsException {
         RegisterRegularUserUseCase.RegisterRegularUserCommand command =
                new RegisterRegularUserUseCase.RegisterRegularUserCommand(
                        VALID_REGULAR_USER_FIRST_NAME,
                        VALID_REGULAR_USER_LAST_NAME,
                        NON_VALID_ACCOUNT_EMAIL,
                        NON_VALID_ACCOUNT_PASSWORD
                );
        registerRegularUserUseCase.addRegularUser(command);
    }

    @Test(expected = ConstraintViolationException.class)
    public void givenRegisterRegularUserCommandIsInvalidThenRegistrationWillSucceed() throws AccountAlreadyExistsException {
        RegisterRegularUserUseCase.RegisterRegularUserCommand command =
                new RegisterRegularUserUseCase.RegisterRegularUserCommand(
                        VALID_REGULAR_USER_FIRST_NAME,
                        VALID_REGULAR_USER_LAST_NAME,
                        EXISTING_REGULAR_USER_EMAIL,
                        EXISTING_REGULAR_USER_PASSWORD2
                );
        registerRegularUserUseCase.addRegularUser(command);
    }
}
