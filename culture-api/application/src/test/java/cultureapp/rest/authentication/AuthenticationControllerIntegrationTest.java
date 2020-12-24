package cultureapp.rest.authentication;

import static cultureapp.common.AuthenticationTestData.*;
import static cultureapp.common.RegularUserTestData.*;

import cultureapp.domain.authentication.command.LoginUseCase;
import cultureapp.domain.user.RegularUser;
import cultureapp.domain.user.RegularUserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static cultureapp.common.AuthenticationTestData.EXISTING_REGULAR_USER_EMAIL;
import static cultureapp.common.AuthenticationTestData.EXISTING_REGULAR_USER_PASSWORD;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class AuthenticationControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RegularUserRepository regularUserRepository;

    /*
    -----------------------
    LOGIN
    -----------------------
    */
    @Test
    public void givenLoginRequestIsValidThenUserLoginWillSucceed() {
        LoginRequest request = new LoginRequest(EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        ResponseEntity<LoginUseCase.LoginDTO> response =
                restTemplate.postForEntity("/api/auth/login", request, LoginUseCase.LoginDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().getToken());
    }

    @Test
    public void givenLoginRequestEmailIsInvalidThenUserLoginWillReturnUnauthorized() {
        LoginRequest request = new LoginRequest(NON_EXISTING_ADMINISTRATOR_EMAIL, NON_EXISTING_ADMINISTRATOR_PASSWORD);
        ResponseEntity<LoginUseCase.LoginDTO> response =
                restTemplate.postForEntity("/api/auth/login", request, LoginUseCase.LoginDTO.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void givenLoginRequestAccountNotActivatedThenUserLoginWillFail() {
        LoginRequest request = new LoginRequest(NOT_ACTIVATED_ACCOUNT_EXISTING_EMAIL, NOT_ACTIVATED_ACCOUNT_EXISTING_PASSWORD);
        ResponseEntity<LoginUseCase.LoginDTO> response =
                restTemplate.postForEntity("/api/auth/login", request, LoginUseCase.LoginDTO.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void givenLoginRequestInvalidThenUserLoginWillReturnBadRequest() {
        LoginRequest request = new LoginRequest(NON_VALID_ACCOUNT_EMAIL, NON_VALID_ACCOUNT_PASSWORD);
        ResponseEntity<LoginUseCase.LoginDTO> response =
                restTemplate.postForEntity("/api/auth/login", request, LoginUseCase.LoginDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /*
    -----------------------
    REGISTER
    -----------------------
    */
    @Test
    public void givenRegisterRequestIsValidThenRegistrationWillSucceed() {
        RegisterRequest request = new RegisterRequest(
            VALID_REGULAR_USER_FIRST_NAME,
                VALID_REGULAR_USER_LAST_NAME,
                NON_EXISTING_REGULAR_USER_EMAIL,
                VALID_PASSWORD
        );
        ResponseEntity<Void> response =
                restTemplate.postForEntity("/api/auth/register", request, Void.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        Optional<RegularUser> regularUserOptional = regularUserRepository.findByAccountEmail(NON_EXISTING_REGULAR_USER_EMAIL);

        assertFalse(regularUserOptional.isEmpty());

        RegularUser regularUser = regularUserOptional.get();
        assertEquals(VALID_REGULAR_USER_FIRST_NAME, regularUser.getFirstName());
        assertEquals(VALID_REGULAR_USER_LAST_NAME, regularUser.getLastName());
        assertEquals(NON_EXISTING_REGULAR_USER_EMAIL, regularUser.getEmail());

        regularUserRepository.delete(regularUser);
    }

    @Test
    public void givenRegisterRequestIsInvalidThenRegistrationWillFail() {
        RegisterRequest request = new RegisterRequest(
                VALID_REGULAR_USER_FIRST_NAME,
                VALID_REGULAR_USER_LAST_NAME,
                NON_VALID_ACCOUNT_EMAIL,
                NON_VALID_ACCOUNT_PASSWORD
        );
        ResponseEntity<Void> response =
                restTemplate.postForEntity("/api/auth/register", request, Void.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void givenRegisterRequestEmailExistsThenRegistrationWillFail() {
        RegisterRequest request = new RegisterRequest(
                VALID_REGULAR_USER_FIRST_NAME,
                VALID_REGULAR_USER_LAST_NAME,
                EXISTING_REGULAR_USER_EMAIL,
                EXISTING_REGULAR_USER_PASSWORD
        );
        ResponseEntity<Void> response =
                restTemplate.postForEntity("/api/auth/register", request, Void.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /*
    -----------------------
    ACTIVATE ACCOUNT
    -----------------------
    */

    @Test
    public void givenAccountIdIsValidAndAccountIsNotActivatedThenActivateAccountWillSucceed() {
        ResponseEntity<Void> response =
                restTemplate.postForEntity(
                        String.format("/api/auth/activate/%d", NOT_ACTIVATED_ACCOUNT_ID),
                        null,
                        Void.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void givenAccountIdIsValidAndAccountIsAlreadyActivatedThenActivateAccountWillFail() {
        ResponseEntity<Void> response =
                restTemplate.postForEntity(
                        String.format("/api/auth/activate/%d", ACTIVATED_ACCOUNT_ID),
                        null,
                        Void.class);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void givenAccountIdIsInvalidThenActivateAccountWillFail() {
        ResponseEntity<Void> response =
                restTemplate.postForEntity(
                        String.format("/api/auth/activate/%d", NON_VALID_ID),
                        null,
                        Void.class);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

}
