package cultureapp.rest.authentication;

import cultureapp.domain.account.command.ActivateAccountUseCase;
import cultureapp.domain.account.exception.AccountAlreadyActivatedException;
import cultureapp.domain.account.exception.AccountAlreadyExistsException;
import cultureapp.domain.account.exception.AccountNotFoundException;
import cultureapp.domain.account.command.ChangePasswordUseCase;
import cultureapp.domain.authentication.command.LoginUseCase;
import cultureapp.domain.authentication.exception.AccountNotActivatedException;
import cultureapp.domain.user.command.RegisterRegularUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Component
@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthenticationController {
    private final LoginUseCase loginUseCase;
    private final RegisterRegularUserUseCase registerRegularUserUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;
    private final ActivateAccountUseCase activateAccountUseCase;

    @PostMapping("/login")
    public ResponseEntity<LoginUseCase.LoginDTO> login(@RequestBody LoginRequest request) throws AccountNotActivatedException {
        LoginUseCase.LoginCommand command =
                new LoginUseCase.LoginCommand(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(loginUseCase.login(command));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest request)
            throws AccountAlreadyExistsException {
        RegisterRegularUserUseCase.RegisterRegularUserCommand command =
                new RegisterRegularUserUseCase.RegisterRegularUserCommand(
                        request.getFirstName(), request.getLastName(), request.getEmail(), request.getPassword());
        registerRegularUserUseCase.addRegularUser(command);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/activate/{accountId}")
    public ResponseEntity<Void> activate(@PathVariable Long accountId)
            throws AccountNotFoundException, AccountAlreadyActivatedException {
        activateAccountUseCase.activateAccount(accountId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
